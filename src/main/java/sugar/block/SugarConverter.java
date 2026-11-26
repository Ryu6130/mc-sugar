package sugar.block;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import sugar.blockentity.ModBlockEntityTypes;
import sugar.blockentity.SugarConverterEntity;

public class SugarConverter extends BaseEntityBlock {
    protected SugarConverter(Properties properties) {
        super(properties);
    }

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof SugarConverterEntity entity) {
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
		if (level instanceof ServerLevel serverLevel && type == ModBlockEntityTypes.SUGAR_CONVERTER) {
			return (innerLevel, pos, state, entity) ->
				SugarConverterEntity.serverTick(serverLevel, pos, state, (SugarConverterEntity)entity);
		} else {
			return null;
		}
	}

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new SugarConverterEntity(arg0, arg1);
    }

	@Override
	protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return level.getBlockEntity(pos) instanceof SugarConverterEntity entity ? entity : null;
	}
}
