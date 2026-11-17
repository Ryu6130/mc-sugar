package sugar.entity;

import java.util.OptionalInt;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

public class BlueAMD extends Monster {
    private OptionalInt chargeTicks = OptionalInt.empty();

    protected BlueAMD(Level level) {
        this(ModEntityTypes.BLUE_AMD, level);
    }

    protected BlueAMD(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        xpReward = XP_REWARD_HUGE;
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
        if (chargeTicks.isEmpty() && getTarget() != null && distanceTo(getTarget()) > 12) {
            chargeTicks = OptionalInt.of(0);
            entityData.set(ABILITY, true);
        }
        if (chargeTicks.isPresent()) {
            int ticks = chargeTicks.getAsInt() + 1;
            chargeTicks = OptionalInt.of(ticks);
            if (entityData.get(ABILITY)) {
                if (ticks == 21 && getTarget() != null) {
                    BlockPos pos = getTarget().blockPosition();
                    if (
                        level.isEmptyBlock(pos.above(1)) &&
                        level.isEmptyBlock(pos.above(2)) &&
                        level.isEmptyBlock(pos.above(3))
                    ) {
                        level.setBlock(
                            pos.above(2),
                            Fluids.WATER.getFlowing(1, true).createLegacyBlock(),
                            3
                        );
                        level.setBlock(
                            pos.above(3),
                            Fluids.WATER.getFlowing(1, true).createLegacyBlock(),
                            3
                        );
                    }
                }
                if (ticks >= 40) {
                    chargeTicks = OptionalInt.empty();
                    entityData.set(ABILITY, false);
                }
            }
            else if (entityData.get(ATTACKING)) {
                if (ticks == 8 && getTarget() != null && isWithinMeleeAttackRange(getTarget())) {
                    doHurtTarget(level, getTarget());
                    if (getTarget() != null) {
                        float rand = random.nextFloat();
                        Holder<MobEffect> effect;
                        if (rand < 0.3) effect = MobEffects.POISON;
                        else if (rand < 0.6) effect = MobEffects.WITHER;
                        else effect = MobEffects.SLOWNESS;
                        getTarget().addEffect(new MobEffectInstance(effect, 150, 1));
                    }
                }
                if (ticks >= 15) {
                    chargeTicks = OptionalInt.empty();
                    entityData.set(ATTACKING, false);
                }
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
            .add(Attributes.MAX_HEALTH, 150)
            .add(Attributes.ARMOR, 25)
            .add(Attributes.MOVEMENT_SPEED, 0.25)
            .add(Attributes.FOLLOW_RANGE, 32)
            .add(Attributes.ATTACK_DAMAGE, 5)
            .add(Attributes.ATTACK_KNOCKBACK, 2)
            .add(Attributes.ATTACK_SPEED, 0.4)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1)
            .add(Attributes.STEP_HEIGHT, 1.2);
	}
    
    private static final EntityDataAccessor<Boolean> ATTACKING =
        SynchedEntityData.defineId(BlueAMD.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ABILITY =
        SynchedEntityData.defineId(BlueAMD.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState abilityAnimationState = new AnimationState();
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
        builder.define(ABILITY, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);

        if (data == ATTACKING) {
            this.attackAnimationState.animateWhen(
                this.entityData.get(ATTACKING), this.tickCount);
        }

        if (data == ABILITY) {
            this.abilityAnimationState.animateWhen(
                this.entityData.get(ABILITY), this.tickCount);
        }
    }
}
