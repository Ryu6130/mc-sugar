package sugar.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;

public record ClickingBlockRecipeInput(BlockState block, ItemStack item) implements RecipeInput {
	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> this.item;
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	public int size() {
		return 1;
	}
}