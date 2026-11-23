package sugar.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class Lollipop extends BlockItem {
    public Lollipop(Block block, Properties properties) {
        super(block, properties);
    }
    
    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState stateForPlacement) {
        ItemStack stack = context.getItemInHand();

        if (stack.isDamaged()) {
            return false;
        }
        
        return super.canPlace(context, stateForPlacement);
    }
}
