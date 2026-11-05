package sugar.block;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.pattern.BlockPattern.BlockPatternMatch;
import net.minecraft.world.level.redstone.Orientation;
import sugar.entity.ModEntityTypes;
import sugar.entity.Sugardian;

public class Sugarrack extends Block {
    private static final BlockPattern PATTERN = BlockPatternBuilder.start()
        .aisle(new String[]{"~^~", "###", "~#~"})
        .where('^', (block) -> block.getState().is(Blocks.BLAST_FURNACE))
        .where('#', (block) -> block.getState().is(ModBlocks.SUGARRACK))
        .where('~', (block) -> block.getState().isAir())
        .build();

    public Sugarrack(Properties properties) {
        super(properties);
    }

    @Override
    protected void neighborChanged(
        BlockState state,
        Level level,
        BlockPos pos,
        Block block,
        @Nullable Orientation orientation,
        boolean movedByPiston
    ) {
        if (level.isClientSide()) return;
        if (pos.getY() < level.getMinY()) return;
        if (level.getDifficulty() == Difficulty.PEACEFUL) return;
        
        BlockPatternMatch match = PATTERN.find(level, pos);
        if (match == null) return;

        Sugardian entity = ModEntityTypes.SUGARDIAN.create(level, EntitySpawnReason.TRIGGERED);
        if (entity == null) return;

        CarvedPumpkinBlock.clearPatternBlocks(level, match);
        BlockPos spawnPos = match.getBlock(1, 2, 0).getPos();

        entity.snapTo(
            spawnPos.getX() + 0.5,
            spawnPos.getY(),
            spawnPos.getZ() + 0.5,
            match.getForwards().getAxis() == Axis.X ? 0.0F : 90.0F, 0.0F);
        entity.yBodyRot = match.getForwards().getAxis() == Axis.X ? 0.0F : 90.0F;
        
        level.addFreshEntity(entity);
        CarvedPumpkinBlock.updatePatternBlocks(level, match);
    }
}
