package sugar.block;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;
import sugar.blockentity.FermentationTankEntity;
import sugar.blockentity.ModBlockEntityTypes;

public class FermentationTank extends DirectionalBlock implements EntityBlock {
    protected FermentationTank(Properties properties) {
        super(properties);
		registerDefaultState(
			this.stateDefinition.any().setValue(FACING, Direction.NORTH)
		);
    }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(
			FACING,
			context.getNearestLookingDirection().getOpposite()
		);
	}

	@Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof FermentationTankEntity entity) {
			player.openMenu(entity);
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(
		Level level,
		BlockState blockState,
		BlockEntityType<T> type
	) {
		if (level instanceof ServerLevel serverLevel && type == ModBlockEntityTypes.FERMENTATION_TANK) {
			return (innerLevel, pos, state, entity) ->
				FermentationTankEntity.serverTick(serverLevel, pos, state, (FermentationTankEntity)entity);
		} else {
			return null;
		}
	}

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new FermentationTankEntity(arg0, arg1);
    }

	@Override
	protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return level.getBlockEntity(pos) instanceof FermentationTankEntity entity ? entity : null;
	}
}
