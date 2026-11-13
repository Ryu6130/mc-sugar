package sugar.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;

public class Sweeper extends Creeper {
    public Sweeper(EntityType<? extends Sweeper> type, Level level) {
        super(type, level);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0;
    }

    public static boolean checkSpawnRules(
        EntityType<Sweeper> type,
        ServerLevelAccessor level,
        EntitySpawnReason spawnReason,
        BlockPos pos,
        RandomSource random
    ) {
        if (!Monster.checkAnyLightMonsterSpawnRules(type, level, spawnReason, pos, random)) {
            return false;
        }

        if (!level.canSeeSky(pos) && !Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random)) {
            return false;
        }

        return true;
    }

   public static AttributeSupplier.Builder createAttributes() {
      return Creeper.createAttributes()
        .add(Attributes.MOVEMENT_SPEED, 0.3)
        .add(Attributes.STEP_HEIGHT, 1.2);
   }
}
