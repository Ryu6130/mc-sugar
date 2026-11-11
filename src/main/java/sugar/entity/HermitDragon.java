package sugar.entity;

import java.util.EnumSet;
import java.util.OptionalInt;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HermitDragon extends Monster {
    private enum AttackPhase {
        Attack,
        Back
    }
    private AttackPhase attackPhase = AttackPhase.Attack;
    private OptionalInt shotTicks = OptionalInt.empty();
    private HermitDragonPart head;
    private HermitDragonPart neck;
    private HermitDragonPart left_wing;
    private HermitDragonPart right_wing;
    private HermitDragonPart tail;
    private HermitDragonPart[] subEntities;

    public void recreateFromPacket(final ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);

        for(int i = 0; i < subEntities.length; ++i) {
            subEntities[i].setId(i + packet.getId() + 1);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isDeadOrDying() && !isNoAi()) {
            Vec3[] oldPos = new Vec3[this.subEntities.length];

            for(int i = 0; i < this.subEntities.length; ++i) {
               oldPos[i] = new Vec3(this.subEntities[i].getX(), this.subEntities[i].getY(), this.subEntities[i].getZ());
            }

            tickPart(head, 0, 2.1, -3.125);
            tickPart(neck, 0, 2.1, -4.625);
            tickPart(left_wing, -2, 3.1, -0.25);
            tickPart(right_wing, 2, 3.1, -0.25);
            tickPart(tail, 0, 2.1, 3.5);

            for(int i = 0; i < this.subEntities.length; ++i) {
                this.subEntities[i].xo = oldPos[i].x;
                this.subEntities[i].yo = oldPos[i].y;
                this.subEntities[i].zo = oldPos[i].z;
                this.subEntities[i].xOld = oldPos[i].x;
                this.subEntities[i].yOld = oldPos[i].y;
                this.subEntities[i].zOld = oldPos[i].z;
            }
        }
    }
    
    private void tickPart(HermitDragonPart part, double offsetX, double offsetY, double offsetZ) {
        float yaw = Mth.DEG_TO_RAD * yBodyRot + Mth.PI;

        double rotatedX = offsetX * Mth.cos(yaw) - offsetZ * Mth.sin(yaw);
        double rotatedZ = offsetX * Mth.sin(yaw) + offsetZ * Mth.cos(yaw);

        part.setPos(
            getX() + rotatedX,
            getY() + offsetY,
            getZ() + rotatedZ
        );
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);

        if (!level().isClientSide()) {
            for (HermitDragonPart part : subEntities) {
                if (part != null && !part.isRemoved()) {
                    part.remove(reason);
                }
            }
        }
    }

    protected HermitDragon(EntityType<? extends HermitDragon> type, Level level) {
        super(type, level);
        xpReward = XP_REWARD_HUGE;
        head = new HermitDragonPart(this, 1.5f, 1f);
        neck = new HermitDragonPart(this, 1.5f, 1.5f);
        left_wing = new HermitDragonPart(this, 2f, 1f);
        right_wing = new HermitDragonPart(this, 2f, 1f);
        tail = new HermitDragonPart(this, 2f, 1.5f);
        subEntities = new HermitDragonPart[]{head, neck, left_wing, right_wing, tail};
        for (HermitDragonPart subEntity : subEntities)
            level.addFreshEntity(subEntity);
        moveControl = new MoveControl<>(this) {
            @Override
            public void tick() {
                if (this.operation == Operation.MOVE_TO) {
                    this.operation = Operation.WAIT;
                    this.mob.setNoGravity(true);
                    double xd = this.wantedX - this.mob.getX();
                    double yd = this.wantedY - this.mob.getY();
                    double zd = this.wantedZ - this.mob.getZ();
                    double dd = xd * xd + yd * yd + zd * zd;
                    if (dd < (double)2.5000003E-7F) {
                        this.mob.setYya(0.0F);
                        this.mob.setZza(0.0F);
                        return;
                    }

                    float yRotD = (float)(Mth.atan2(zd, xd) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yRotD, 4.0F));
                    float speed;
                    if (this.mob.onGround()) {
                        speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    } else {
                        speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                    }

                    this.mob.setSpeed(speed);
                    double sd = Math.sqrt(xd * xd + zd * zd);
                    if (Math.abs(yd) > (double)1.0E-5F || Math.abs(sd) > (double)1.0E-5F) {
                        float xRotD = (float)(-(Mth.atan2(yd, sd) * (double)(180F / (float)Math.PI)));
                        this.mob.setXRot(this.rotlerp(this.mob.getXRot(), xRotD, (float)20));
                        this.mob.setYya(yd > (double)0.0F ? speed : -speed);
                    }
                } else {
                    this.mob.setNoGravity(false);

                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                }
            }
        };
        navigation = new FlyingPathNavigation(this, level);
    }

    @Override
    public void travel(Vec3 input) {
        if (isNoGravity()) {
            travelFlying(input, 0.1f);
        } else {
            super.travel(input);
        }
    }
    
    protected HermitDragon(Level level) {
        this(ModEntityTypes.HERMIT_DRAGON, level);
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageModifier, DamageSource damageSource) {
        return false;
    }

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 200)
            .add(Attributes.MOVEMENT_SPEED, 0.4)
            .add(Attributes.FLYING_SPEED, 1)
            .add(Attributes.FOLLOW_RANGE, 48)
            .add(Attributes.ATTACK_DAMAGE, 10)
            .add(Attributes.ATTACK_KNOCKBACK, 2)
            .add(Attributes.ATTACK_SPEED, 2)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1);
	}

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, true) {
            @Override
            public boolean canUse() {
                return attackPhase == AttackPhase.Attack && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return attackPhase == AttackPhase.Attack && super.canContinueToUse();
            }

            @Override
            protected void checkAndPerformAttack(LivingEntity target) {
                if (this.canPerformAttack(target))
                    attackPhase = AttackPhase.Back;
                super.checkAndPerformAttack(target);
            }
        });
        this.goalSelector.addGoal(3, new Goal() {
            @Override
            public boolean canUse() {
                return attackPhase == AttackPhase.Back;
            }

            @Override
            public boolean canContinueToUse() {
                return navigation.isInProgress() && super.canContinueToUse();
            }

            @Override
            public void start() {
                navigation.moveTo(
                    position().x + (random.nextDouble() - 0.5) * 16,
                    position().y + 20,
                    position().z + (random.nextDouble() - 0.5) * 16,
                    1);
                shotTicks = OptionalInt.of(0);
            }

            @Override
            public void tick() {
                if (getTarget() != null)
                    lookControl.setLookAt(getTarget(), 30, 30);
            }

            @Override
            public void stop() {
                attackPhase = AttackPhase.Attack;
            }

            {
                setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            }
        });
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 0.6));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        boolean damaged = super.hurtServer(level, source, damage);
        if (damaged) attackPhase = AttackPhase.Back;
        return damaged;
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        removeWalls(level, getAttackBoundingBox(1));
        if (getTarget() == null) {
            shotTicks = OptionalInt.empty();
            entityData.set(JAW_ROT, 0f);
        }
        else if (shotTicks.isPresent()) {
            int ticks = shotTicks.getAsInt() + 1;
            shotTicks = OptionalInt.of(ticks);
            if (ticks <= 20) {
                entityData.set(JAW_ROT, Mth.PI / 80 * ticks);
            }
            if (ticks == 30 || ticks == 40 || ticks == 50 || ticks == 60 || ticks == 70) {
                Vec3 pos = head != null ? head.position() : getEyePosition();
                Vec3 dir = getTarget().getEyePosition().subtract(pos).normalize();
                WindCharge windCharge = new WindCharge(level, pos.x, pos.y, pos.z, dir);
                windCharge.setOwner(this);
                windCharge.setDeltaMovement(dir.scale(4));
                level.addFreshEntity(windCharge);
            }
            if (ticks >= 80) {
                entityData.set(JAW_ROT, Mth.PI / 80 * (20 - ticks + 80));
            }
            if (ticks >= 100) {
                shotTicks = OptionalInt.empty();
            }
        }
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

    private static final EntityDataAccessor<Float> JAW_ROT =
        SynchedEntityData.defineId(
            HermitDragon.class,
            EntityDataSerializers.FLOAT
        );

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(JAW_ROT, 0.0F);
    }

    public float getJawRot() {
        return entityData.get(JAW_ROT);
    }

    public static boolean checkSpawnRules(
        EntityType<HermitDragon> type,
        ServerLevelAccessor level,
        EntitySpawnReason spawnReason,
        BlockPos pos,
        RandomSource random
    ) {
        if (!Monster.checkAnyLightMonsterSpawnRules(type, level, spawnReason, pos, random)) {
            return false;
        }

        if (!level.canSeeSky(pos)) {
            return false;
        }

        if (level.getRawBrightness(pos, 0) < 10) {
            return false;
        }

        return true;
    }
}
