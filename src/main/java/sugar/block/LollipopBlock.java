package sugar.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LollipopBlock extends TransparentBlock {
    public LollipopBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(ModBlocks.SUGAR_CLOD);
    }
}
