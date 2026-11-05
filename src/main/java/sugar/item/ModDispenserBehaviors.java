package sugar.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModDispenserBehaviors {
    public static void initialize() {
        DispenserBlock.registerBehavior(
            ModItems.SUGAR_CELL,
            new DefaultDispenseItemBehavior() {
                protected ItemStack execute(BlockSource source, ItemStack stack) {
                    BlockPos dispenserPos = source.pos();
                    Level level = source.level();

                    Direction direction = source.blockEntity()
                        .getBlockState().getValue(DispenserBlock.FACING);

                    BlockPos pos = dispenserPos.relative(direction);
                    BlockPos above = pos.above();

                    if (!level.getBlockState(pos).is(Blocks.SUGAR_CANE)) {
                        return super.execute(source, stack);
                    }

                    BlockState aboveState = level.getBlockState(above);
                    if (!aboveState.is(Blocks.AIR)) {
                        if (aboveState.is(Blocks.SUGAR_CANE)) {
                            pos = above;
                            above = pos.above();
                        }
                        if (!level.getBlockState(above).is(Blocks.AIR))
                            return stack;
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
                    
                    if (level instanceof ServerLevel serverLevel)
                        stack.hurtAndBreak(1, serverLevel, null, _ -> {});

                    return stack;
                };
            }
        );
    }
}
