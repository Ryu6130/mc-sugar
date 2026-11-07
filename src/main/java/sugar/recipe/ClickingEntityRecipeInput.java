package sugar.recipe;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ClickingEntityRecipeInput(Entity entity, ItemStack item) implements RecipeInput {
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