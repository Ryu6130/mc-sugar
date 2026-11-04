package sugar.item;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;

public class SugarStorageContainer implements Container {
    private final ItemStack storageStack;
    private final NonNullList<ItemStack> items =
            NonNullList.withSize(9, ItemStack.EMPTY);

    public SugarStorageContainer(ItemStack storageStack) {
        this.storageStack = storageStack;

        ItemContainerContents contents = storageStack.getOrDefault(
            DataComponents.CONTAINER,
            ItemContainerContents.EMPTY
        );

        contents.copyInto(items);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(items, slot, amount);
        setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        setChanged();
    }

    @Override
    public void setChanged() {
        storageStack.set(
            DataComponents.CONTAINER,
            ItemContainerContents.fromItems(items)
        );
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < getContainerSize(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
        setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (stack.is(ModItems.SUGAR_STORAGE) || stack.is(Items.SHULKER_BOX)) {
            return false;
        }

        return Container.super.canPlaceItem(slot, stack);
    }
}
