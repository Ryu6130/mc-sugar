package sugar.menu;

import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DesugarerMenu extends AbstractContainerMenu {
    private static final int SLOTS_COUNT = 2;

	private static final int CONTAINER_START = 0;
	private static final int CONTAINER_END = SLOTS_COUNT;
	private static final int INVENTORY_START = CONTAINER_END;
	private static final int INVENTORY_END = INVENTORY_START + Inventory.INVENTORY_SIZE;

	private static final int INVENTORY_START_X = 8;
	private static final int INVENTORY_START_Y = 84;

	private final ContainerData data;

	public DesugarerMenu(final int containerId, final Inventory inventory) {
		this(containerId, inventory, new SimpleContainer(SLOTS_COUNT), new SimpleContainerData(2), ContainerLevelAccess.NULL);
	}

	public DesugarerMenu(final int containerId, final Inventory inventory, final ContainerLevelAccess access) {
		this(containerId, inventory, new SimpleContainer(SLOTS_COUNT), new SimpleContainerData(2), access);
	}

	public DesugarerMenu(
		final int containerId,
		final Inventory inventory,
		final Container container,
		final ContainerData data,
		final ContainerLevelAccess access
	) {
		super(ModMenuTypes.DESUGARER, containerId);
		checkContainerSize(container, SLOTS_COUNT);
		this.container = container;
		this.data = data;

		container.startOpen(inventory.player);

		this.addSlot(new Slot(
            this.container,
            0,
            56,
            35
        ));
		this.addSlot(new Slot(
            this.container,
            1,
            116,
            35
        ) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return false;
            }
        });

		this.addStandardInventorySlots(inventory, INVENTORY_START_X, INVENTORY_START_Y);
      	this.addDataSlots(data);
	}

	private final Container container;

	public float getProgress() {
      int current = this.data.get(0);
      int total = this.data.get(1);
      return total != 0 && current != 0 ? Mth.clamp((float)current / (float)total, 0.0F, 1.0F) : 0.0F;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		Slot slot = this.slots.get(slotIndex);

		if (!slot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getItem();
		ItemStack clicked = stack.copy();

		if (slotIndex < CONTAINER_END) {
			if (!this.moveItemStackTo(stack, INVENTORY_START, INVENTORY_END, true)) {
				return ItemStack.EMPTY;
			}
		} else {
			if (!this.moveItemStackTo(stack, CONTAINER_START, CONTAINER_END, false)) {
				return ItemStack.EMPTY;
			}
		}

		if (stack.isEmpty()) {
			slot.setByPlayer(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}

		return clicked;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}
}
