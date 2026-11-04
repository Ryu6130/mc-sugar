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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;
import sugar.blockentity.DesugarerEntity;
import sugar.blockentity.ModBlockEntityTypes;

public class Desugarer extends HorizontalDirectionalBlock implements EntityBlock {
	protected Desugarer(Properties properties) {
		super(properties);
		registerDefaultState(
			this.stateDefinition.any().setValue(FACING, Direction.NORTH)
		);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(
			FACING,
			context.getHorizontalDirection().getOpposite()
		);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof DesugarerEntity entity) {
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
		if (level instanceof ServerLevel serverLevel && type == ModBlockEntityTypes.DESUGARER) {
			return (innerLevel, pos, state, entity) ->
				DesugarerEntity.serverTick(serverLevel, pos, state, (DesugarerEntity)entity);
		} else {
			return null;
		}
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new DesugarerEntity(pos, state);
	}

	@Override
	protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return level.getBlockEntity(pos) instanceof DesugarerEntity entity ? entity : null;
	}
}
