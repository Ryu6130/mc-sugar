package sugar.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SugarCell extends Item {
    public SugarCell(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        InteractionHand hand = context.getHand();
        BlockPos pos = context.getClickedPos();
        BlockPos above = pos.above();
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS_SERVER;
        }

        if (!level.getBlockState(pos).is(Blocks.SUGAR_CANE)) {
            return InteractionResult.PASS;
        }

        BlockState aboveState = level.getBlockState(above);
        if (!aboveState.is(Blocks.AIR)) {
            if (aboveState.is(Blocks.SUGAR_CANE)) {
                pos = above;
                above = pos.above();
            }
            if (!level.getBlockState(above).is(Blocks.AIR))
                return InteractionResult.PASS;
        }

        level.setBlock(above, Blocks.SUGAR_CANE.defaultBlockState(), 3);
        level.playSound(
            null,
            above,
            Blocks.SUGAR_CANE.defaultBlockState().getSoundType().getPlaceSound(),
            SoundSource.BLOCKS,
            1.0F,
            1.0F
        );

        if (player == null) {
            if (level instanceof ServerLevel serverLevel)
                stack.hurtAndBreak(1, serverLevel, null, _ -> {});
        } else {
            stack.hurtAndBreak(1, player, hand);
        }

        return InteractionResult.CONSUME;
    }
}
