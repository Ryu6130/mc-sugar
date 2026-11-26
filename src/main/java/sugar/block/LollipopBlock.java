package sugar.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LollipopBlock extends VegetationBlock {
    public LollipopBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.SUGAR_CLOD);
    }
}
