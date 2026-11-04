package sugar.block;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;

public class SugarCube extends Block {
	public SugarCube(Properties properties) {
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
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);

        if (level.getBlockState(pos.above()).is(Blocks.ANVIL)) {
            level.setBlock(pos, ModBlocks.KONPEITO_BLOCK.defaultBlockState(), 3);
        }
    }
}
