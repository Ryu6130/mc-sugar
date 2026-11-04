package sugar.item;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sugar.menu.SugarStorageMenu;

public class SugarStorage extends Item {
    public SugarStorage(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayer)) {
            return InteractionResult.PASS;
        }
        
        player.openMenu(new ExtendedMenuProvider<Integer>() {
            @Override
            public Integer getScreenOpeningData(ServerPlayer player) {
                return hand == InteractionHand.MAIN_HAND ? 0 : 1;
            }

            @Override
            public Component getDisplayName() {
                return Component.translatable("item.sugar.sugar_storage");
            }

            @Override
            public AbstractContainerMenu createMenu(
                    int id,
                    Inventory inventory,
                    Player player
            ) {
                return new SugarStorageMenu(id, inventory, hand == InteractionHand.MAIN_HAND ? 0 : 1);
            }
        });
        
        return InteractionResult.SUCCESS;
    }
}
