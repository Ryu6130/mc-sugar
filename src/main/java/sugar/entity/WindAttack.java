package sugar.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class WindAttack extends ThrowableProjectile {
    private int lifeTime = 0;

    public WindAttack(Level level) {
        this(ModEntityTypes.WIND_ATTACK, level);
    }

    public WindAttack(EntityType<? extends WindAttack> type, Level level) {
        super(type, level);
        setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();

        if (++lifeTime > 200) discard();

        if (level() instanceof ServerLevel level) {
            level.sendParticles(
                ParticleTypes.CLOUD,
                getX(),
                getY(),
                getZ(),
                1,
                0.3,
                0.5,
                0.3,
                0.02
            );
        }
    }

    @Override
    protected void updateRotation() {
        Vec3 movement = this.getDeltaMovement();
        double sd = movement.horizontalDistance();
        this.setXRot((float)(Mth.atan2(movement.y, sd) * (double)(180F / (float)Math.PI)));
        this.setYRot((float)(Mth.atan2(movement.x, movement.z) * (double)(180F / (float)Math.PI)));
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);

        discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        
        Entity owner = getOwner() != null ? getOwner() : this;
        if (
            level() instanceof ServerLevel level &&
            hitResult.getEntity() instanceof LivingEntity entity &&
            entity.hurtServer(
                level,
                level.damageSources().indirectMagic(
                    entity,
                    owner
                ),
                3
            )
        ) {
            Vec3 direction = owner.position().subtract(entity.position()).normalize();

            entity.knockback(
                0.5,
                direction.x,
                direction.z,
                level.damageSources().indirectMagic(
                    entity,
                    owner
                ),
                3
            );

            discard();
        }
    }

    @Override
    protected void defineSynchedData(Builder entityData) {}

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("LifeTime", lifeTime);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        lifeTime = input.getIntOr("LifeTime", 0);
    }
}
