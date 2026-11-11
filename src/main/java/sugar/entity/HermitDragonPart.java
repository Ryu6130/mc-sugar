package sugar.entity;

import javax.annotation.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class HermitDragonPart extends Entity {
    public @Nullable HermitDragon parentMob = null;
    private EntityDimensions size = EntityDimensions.scalable(0, 0);

    public HermitDragonPart(final HermitDragon parentMob, final float w, final float h) {
        super(ModEntityTypes.HERMIT_DRAGON_PART, parentMob.level());
        this.parentMob = parentMob;
        this.size = EntityDimensions.scalable(w, h);
        this.refreshDimensions();
    }

    public HermitDragonPart(EntityType<? extends HermitDragonPart> type, Level level) {
        super(type, level);
    }

    private static final EntityDataAccessor<Integer> PARENT_ID =
        SynchedEntityData.defineId(
            HermitDragonPart.class,
            EntityDataSerializers.INT
        );

    private static final EntityDataAccessor<Float> WIDTH =
        SynchedEntityData.defineId(
            HermitDragonPart.class,
            EntityDataSerializers.FLOAT
        );

    private static final EntityDataAccessor<Float> HEIGHT =
        SynchedEntityData.defineId(
            HermitDragonPart.class,
            EntityDataSerializers.FLOAT
        );

    @Override
    protected void defineSynchedData(final SynchedEntityData.Builder builder) {
        builder.define(PARENT_ID, -1);
        builder.define(WIDTH, 0f);
        builder.define(HEIGHT, 0f);
    }

    @Override
    public void tick() {
        super.tick();

        if (parentMob == null && level() instanceof ClientLevel) {
            int id = this.getEntityData().get(PARENT_ID);
            if (id != -1 && level().getEntity(id) instanceof HermitDragon dragon) {
                parentMob = dragon;
                size = EntityDimensions.scalable(entityData.get(WIDTH), entityData.get(HEIGHT));
                this.refreshDimensions();
            }
        }
        if (parentMob != null) {
            HermitDragon entity = parentMob;
            if (!entity.isRemoved()) {
                entityData.set(PARENT_ID, entity.getId());
                entityData.set(WIDTH, size.width());
                entityData.set(HEIGHT, size.height());
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(final ValueInput input) {}

    @Override
    protected void addAdditionalSaveData(final ValueOutput output) {}

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return this.parentMob != null ? this.parentMob.getPickResult() : null;
    }

    @Override
    public final boolean hurtServer(final ServerLevel level, final DamageSource source, final float damage) {
        return this.isInvulnerableToBase(source)
            ? false : parentMob == null
            ? false : this.parentMob.hurtServer(level, source, damage);
    }

    @Override
    public boolean is(final Entity other) {
        return this == other || (parentMob != null && parentMob.is(other));
    }

    @Override
    public EntityDimensions getDimensions(final Pose pose) {
        return this.size;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }
}
