package sugar.entity;

import java.util.OptionalInt;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class Sugardian extends Monster {
    private OptionalInt chargeTicks = OptionalInt.empty();

    protected Sugardian(EntityType<? extends Sugardian> type, Level level) {
        super(type, level);
        this.xpReward = XP_REWARD_HUGE;
    }
    
	public Sugardian(Level world) {
		this(ModEntityTypes.SUGARDIAN, world);
	}

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, false) {
            @Override
            protected void checkAndPerformAttack(LivingEntity target) {
                if (
                    isWithinMeleeAttackRange(getTarget()) &&
                    getSensing().hasLineOfSight(target) &&
                    chargeTicks.isEmpty()
                ) {
                    chargeTicks = OptionalInt.of(0);
                    this.mob.getEntityData().set(ATTACKING, true);
                    this.resetAttackCooldown();
                }

            }
        });
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }
    
    @Override
    public void customServerAiStep(ServerLevel level) {
        if (chargeTicks.isPresent()) {
            int ticks = chargeTicks.getAsInt() + 1;
            chargeTicks = OptionalInt.of(ticks);

            if (ticks == 22 && getTarget() != null && isWithinMeleeAttackRange(getTarget())) {
                doHurtTarget(level, getTarget());
            }
            if (ticks >= 40) {
                chargeTicks = OptionalInt.empty();
                entityData.set(ATTACKING, false);
            }
        }
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        AABB hitbox = target.getBoundingBox();
        return getAttackBoundingBox(2).intersects(hitbox);
    }

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 80)
            .add(Attributes.ARMOR, 10)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.FOLLOW_RANGE, 24)
            .add(Attributes.ATTACK_DAMAGE, 9)
            .add(Attributes.ATTACK_KNOCKBACK, 1.5)
            .add(Attributes.ATTACK_SPEED, 1.5)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1);
	}

    private static final EntityDataAccessor<Boolean> ATTACKING =
        SynchedEntityData.defineId(Sugardian.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    @Override
    public void die(DamageSource source) {
        super.die(source);
        deathAnimationState.start(tickCount);
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);

        if (data == ATTACKING) {
            this.attackAnimationState.animateWhen(
                this.entityData.get(ATTACKING), this.tickCount);
        }
    }
}
