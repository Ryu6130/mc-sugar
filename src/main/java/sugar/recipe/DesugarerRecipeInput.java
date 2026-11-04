package sugar.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record DesugarerRecipeInput(ItemStack ingredient) implements RecipeInput {
	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> this.ingredient;
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	public int size() {
		return 1;
	}
}