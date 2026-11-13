package sugar.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Candied extends Zombie {
    public Candied(EntityType<? extends Candied> type, Level level) {
        super(type, level);
    }

    public static boolean checkSpawnRules(
        EntityType<Candied> type,
        ServerLevelAccessor level,
        EntitySpawnReason spawnReason,
        BlockPos pos,
        RandomSource random
    ) {
        return Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
            .add(Attributes.MOVEMENT_SPEED, 0.21)
            .add(Attributes.ARMOR, 3);
    }
}
