package sugar.entity;

import java.util.OptionalInt;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BerryGod extends Monster {
    private OptionalInt chargeTicks = OptionalInt.empty();

    protected BerryGod(EntityType<? extends BerryGod> type, Level level) {
        super(type, level);
        this.xpReward = XP_REWARD_BOSS;
    }
    
	public BerryGod(Level world) {
		this(ModEntityTypes.BERRY_GOD, world);
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
                    resetAttackCooldown();
                }
            }
        });
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (level() instanceof ServerLevel level) {
            if (chargeTicks.isEmpty()) {
                entityData.set(ATTACKING, false);
                entityData.set(SHOTING, false);
            }
            if (chargeTicks.isEmpty() && getTarget() != null && distanceTo(getTarget()) > 8) {
                chargeTicks = OptionalInt.of(0);
                entityData.set(SHOTING, true);
            }
            if (chargeTicks.isEmpty() && checkWalls(level, getAttackBoundingBox(1))) {
                chargeTicks = OptionalInt.of(0);
                entityData.set(ATTACKING, true);
            }
            if (chargeTicks.isPresent() && entityData.get(ATTACKING)) {
                int ticks = chargeTicks.getAsInt() + 1;
                chargeTicks = OptionalInt.of(ticks);

                if (ticks == 18) {
                    if (getTarget() != null && this.isWithinMeleeAttackRange(getTarget()))
                        doHurtTarget(level, getTarget());
                    removeWalls(level, getAttackBoundingBox(1.5));
                }
                if (ticks >= 30) {
                    chargeTicks = OptionalInt.empty();
                    entityData.set(ATTACKING, false);
                }
            }
            if (chargeTicks.isPresent() && entityData.get(SHOTING)) {
                int ticks = chargeTicks.getAsInt() + 1;
                chargeTicks = OptionalInt.of(ticks);

                if (ticks == 9) {
                    Vec3 pos = getEyePosition();
                    PrimedTnt tnt = new PrimedTnt(level, pos.x, pos.y, pos.z, this);
                    tnt.setDeltaMovement(getHeadLookAngle().scale(1));
                    level.addFreshEntity(tnt);
                }
                if (ticks >= 30) {
                    chargeTicks = OptionalInt.empty();
                    entityData.set(SHOTING, false);
                }
            }
        }
    }

    private boolean checkWalls(ServerLevel level, AABB box) {
        int x0 = Mth.floor(box.minX);
        int y0 = Mth.floor(box.minY);
        int z0 = Mth.floor(box.minZ);

        int x1 = Mth.floor(box.maxX);
        int y1 = Mth.floor(box.maxY);
        int z1 = Mth.floor(box.maxZ);

        boolean hitWall = false;

        for (int x = x0; x <= x1; x++) {
            for (int y = y0; y <= y1; y++) {
                for (int z = z0; z <= z1; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(pos);

                    if (
                        !state.isAir() &&
                        !state.is(BlockTags.DRAGON_TRANSPARENT) &&
                        level.getGameRules().get(GameRules.MOB_GRIEFING) &&
                        !state.is(BlockTags.DRAGON_IMMUNE)
                    ) hitWall = true;
                }
            }
        }

        return hitWall;
    }

    private void removeWalls(ServerLevel level, AABB box) {
        int x0 = Mth.floor(box.minX);
        int y0 = Mth.floor(box.minY);
        int z0 = Mth.floor(box.minZ);

        int x1 = Mth.floor(box.maxX);
        int y1 = Mth.floor(box.maxY);
        int z1 = Mth.floor(box.maxZ);

        for (int x = x0; x <= x1; x++) {
            for (int y = y0; y <= y1; y++) {
                for (int z = z0; z <= z1; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(pos);

                    if (
                        !state.isAir() &&
                        !state.is(BlockTags.DRAGON_TRANSPARENT) &&
                        !state.is(BlockTags.DRAGON_IMMUNE) &&
                        level.getGameRules().get(GameRules.MOB_GRIEFING)
                    ) level.removeBlock(pos, false);
                }
            }
        }
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        AABB hitbox = target.getBoundingBox();
        return getAttackBoundingBox(1.5).intersects(hitbox);
    }

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 300)
            .add(Attributes.ARMOR, 8)
            .add(Attributes.MOVEMENT_SPEED, 0.35)
            .add(Attributes.FOLLOW_RANGE, 32)
            .add(Attributes.ATTACK_DAMAGE, 12)
            .add(Attributes.ATTACK_KNOCKBACK, 2)
            .add(Attributes.ATTACK_SPEED, 2)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.9);
	}

    private static final EntityDataAccessor<Boolean> ATTACKING =
        SynchedEntityData.defineId(BerryGod.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHOTING =
        SynchedEntityData.defineId(BerryGod.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState shotAnimationState = new AnimationState();
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
        builder.define(SHOTING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);

        if (data == ATTACKING) {
            this.attackAnimationState.animateWhen(
                this.entityData.get(ATTACKING), this.tickCount);
        }

        if (data == SHOTING) {
            this.shotAnimationState.animateWhen(
                this.entityData.get(SHOTING), this.tickCount);
        }
    }
}
