package sugar.blockentity;

import java.util.List;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import sugar.item.ModItems;
import sugar.menu.SugarConverterMenu;

public class SugarConverterEntity extends BlockEntity implements WorldlyContainer, ContainerData, MenuProvider {
    public static final int CONTAINER_SIZE = 3;
	private final NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);  
    private int progress = 0;
	private int total = 0;

	public SugarConverterEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntityTypes.SUGAR_CONVERTER, worldPosition, blockState);
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
		final SugarConverterEntity entity
	) {
		ItemStack stack = entity.getItem(0);
		ItemStack fuel = entity.getItem(1);
		ItemStack resultSlot = entity.getItem(2);

		if (!stack.isEmpty() && !fuel.isEmpty() && fuel.is(ModItems.SUGAR_CELL) && !stack.is(Items.SUGAR)) {
			entity.total = 60;
			entity.progress++;

			if (entity.progress < entity.total) return;

			ItemStack result = new ItemStack(Items.SUGAR, 1);
			result.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(List.of(
				stack.copy()
			)));

			if (
				ItemStack.isSameItemSameComponents(resultSlot, result) &&
				result.count() + resultSlot.count() <= result.getMaxStackSize()
			) {
				resultSlot.grow(result.count());
				entity.progress = 0;
				entity.setItem(0, ItemStack.EMPTY);;
				entity.setChanged();
			} else if (resultSlot.isEmpty()) {
				entity.setItem(2, result);
				entity.progress = 0;
				entity.setItem(0, ItemStack.EMPTY);;
				entity.setChanged();
			} else return;
			
			int damage = fuel.getDamageValue() + 1;
			if (damage >= fuel.getMaxDamage()) fuel.shrink(1);
			else fuel.setDamageValue(damage);
		} else if (
			!stack.isEmpty() &&
			!fuel.isEmpty() &&
			stack.is(Items.SUGAR) &&
			stack.has(DataComponents.CONTAINER) &&
			fuel.is(ModItems.OMNI_ESSENCE)
		) {
			entity.total = 60;
			entity.progress++;

			if (entity.progress < entity.total) return;
			
			ItemStack result = stack.get(DataComponents.CONTAINER).copyOne();

			if (
				ItemStack.isSameItemSameComponents(resultSlot, result) &&
				result.count() + resultSlot.count() <= result.getMaxStackSize()
			) {
				resultSlot.grow(result.count());
				entity.progress = 0;
				stack.shrink(1);
				entity.setChanged();
			} else if (resultSlot.isEmpty()) {
				entity.setItem(2, result);
				entity.progress = 0;
				stack.shrink(1);
				entity.setChanged();
			} else return;
			
			fuel.shrink(1);
		} else {
			entity.total = 0;
			entity.progress = 0;
		}
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return new SugarConverterMenu(containerId, inventory, this, this);
    }

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.sugar.sugar_converter");
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
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        if (direction == null) return false;

        return switch (direction) {
            case UP -> slot == 0;
            case DOWN -> false;
            default -> slot == 1;
        };
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return direction == Direction.DOWN && slot == 2;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return switch (direction) {
            case UP -> new int[]{0};
            case DOWN -> new int[]{2};
            default -> new int[]{1};
        };
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
