package sugar.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import sugar.entity.DesertDessertRenderer;
import sugar.entity.ModEntityModelLayers;
import sugar.entity.ModEntityTypes;
import sugar.menu.DesugarerScreen;
import sugar.menu.ModMenuTypes;
import sugar.menu.SugarStorageScreen;

public class SugarClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.DESUGARER, DesugarerScreen::new);
        MenuScreens.register(ModMenuTypes.SUGAR_STORAGE, SugarStorageScreen::new);
		ModEntityModelLayers.registerModelLayers();
        EntityRenderers.register(ModEntityTypes.DESERT_DESSERT, DesertDessertRenderer::new);
    }
}
