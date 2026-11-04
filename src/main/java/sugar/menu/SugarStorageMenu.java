package sugar.menu;

import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sugar.item.ModItems;
import sugar.item.SugarStorageContainer;

public class SugarStorageMenu extends AbstractContainerMenu {
    private static final int SLOTS_COUNT = 3 * 3;

	private static final int CONTAINER_START = 0;
	private static final int CONTAINER_END = SLOTS_COUNT;
	private static final int INVENTORY_START = CONTAINER_END;
	private static final int INVENTORY_END = INVENTORY_START + Inventory.INVENTORY_SIZE;

	private final Container container;

	public SugarStorageMenu(final int containerId, final Inventory inventory, int hand) {
		this(containerId, inventory,
			new SugarStorageContainer(inventory.player.getItemInHand(hand == 0 ?
				InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND)));
	}

	public SugarStorageMenu(
		final int containerId,
		final Inventory inventory,
		final Container container
	) {
		super(ModMenuTypes.SUGAR_STORAGE, containerId);
		checkContainerSize(container, SLOTS_COUNT);
        this.container = container;
        
		container.startOpen(inventory.player);

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				final int slot = x + y * 3;
				this.addSlot(new Slot(
                    this.container,
                    slot,
                    62 + x * SLOT_SIZE,
                    17 + y * SLOT_SIZE
				) {
					@Override
					public boolean mayPlace(ItemStack stack) {
						if (stack.is(ModItems.SUGAR_STORAGE) || stack.is(Items.SHULKER_BOX)) {
							return false;
						}

						return true;
					}
				});
			}
		}
        
		this.addStandardInventorySlots(inventory, 8, 84);
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
