package sugar.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record FermentingRecipeInput(ItemStack base, ItemStack starter) implements RecipeInput {
	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> this.base;
			case 1 -> this.starter;
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	public int size() {
		return 2;
	}
}