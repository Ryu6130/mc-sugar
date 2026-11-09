package sugar.blockentity;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import sugar.menu.FermentationTankMenu;
import sugar.recipe.FermentingRecipe;
import sugar.recipe.FermentingRecipeInput;
import sugar.recipe.ModRecipes;

public class FermentationTankEntity extends BlockEntity implements Container, ContainerData, MenuProvider {
	public static final int CONTAINER_SIZE = 3;
	private final NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private int progress = 0;
	private int total = 0;

    public FermentationTankEntity(BlockPos worldPosition, BlockState blockState) {
		super(ModBlockEntityTypes.FERMENTATION_TANK, worldPosition, blockState);
	}
	
	@Override
	protected void saveAdditional(ValueOutput output) {
		output.putInt("progress", this.progress);
		output.putInt("total", this.total);
		ContainerHelper.saveAllItems(output, items);

		super.saveAdditional(output);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		this.progress = input.getIntOr("progress", 0);
		this.total = input.getIntOr("total", 0);
		ContainerHelper.loadAllItems(input, items);
	}

	public static void serverTick(
		final ServerLevel level,
		final BlockPos pos,
		BlockState state,
		final FermentationTankEntity entity
	) {
		FermentingRecipeInput recipeInput = new FermentingRecipeInput(entity.getItem(0), entity.getItem(1));
		Optional<RecipeHolder<FermentingRecipe>> maybeRecipe = level.recipeAccess().getRecipeFor(ModRecipes.FERMENTING, recipeInput, level);
		
		if (maybeRecipe.isPresent()) {
			RecipeHolder<FermentingRecipe> recipeHolder = maybeRecipe.get();
			FermentingRecipe recipe = recipeHolder.value();

			entity.total = recipe.getTime();
			entity.progress++;
			if (entity.progress < entity.total) {
				return;
			}

			ItemStack result = recipe.assemble(recipeInput);
			if (
				ItemStack.isSameItemSameComponents(entity.getItem(2), result) &&
				result.count() + entity.getItem(1).count() <= result.getMaxStackSize()
			) {
				entity.getItem(2).grow(result.count());
				entity.progress = 0;
				entity.getItem(0).shrink(1);
				entity.setChanged();
			} else if (entity.getItem(2).isEmpty()) {
				entity.setItem(2, result);
				entity.progress = 0;
				entity.getItem(0).shrink(1);
				entity.setChanged();
			}
		} else {
			entity.total = 0;
			entity.progress = 0;
		}
	}

	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return new FermentationTankMenu(containerId, inventory, this, this);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.sugar.fermentation_tank");
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

    @Override
    public void clearContent() {
        for (int i = 0; i < CONTAINER_SIZE; i++) {
            items.set(i, ItemStack.EMPTY);
        }
        setChanged();
    }

	@Override
	public int getContainerSize() {
		return CONTAINER_SIZE;
	}

	@Override
	public ItemStack getItem(int slot) {
		return items.get(slot);
	}

	@Override
	public boolean isEmpty() {
		return items.stream().allMatch(stack -> stack.isEmpty());
	}

	@Override
	public ItemStack removeItem(int slot, int count) {
		ItemStack stack = ContainerHelper.removeItem(items, slot, count);
		setChanged();
		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(items, slot);
	}

	@Override
	public void setItem(int slot, ItemStack itemStack) {
		items.set(slot, itemStack);
		setChanged();
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack itemStack) {
		return slot == 0;
	}

	@Override
	public boolean canTakeItem(Container into, int slot, ItemStack itemStack) {
		return slot == 2;
	}
    
	@Override
	public int get(int dataId) {
		return switch (dataId) {
			case 0 -> progress;
			default -> total;
		};
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public void set(int dataId, int value) {
		if (dataId == 0) {
			progress = value;
		} else {
			total = value;
		}
	}
}
