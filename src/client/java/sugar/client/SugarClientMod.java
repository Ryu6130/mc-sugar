package sugar.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import sugar.SugarMod;
import sugar.ability.ModAbility;
import sugar.block.ModBlocks;
import sugar.client.render.FirstPersonAnimationRegistry;
import sugar.entity.AlligantNestRenderer;
import sugar.entity.AlligantRenderer;
import sugar.entity.ArcRenderer;
import sugar.entity.BerryGodRenderer;
import sugar.entity.BlackHoleRenderer;
import sugar.entity.CandiedRenderer;
import sugar.entity.DesertDessertRenderer;
import sugar.entity.GnomeRenderer;
import sugar.entity.HermitDragonRenderer;
import sugar.entity.ModEntityModelLayers;
import sugar.entity.ModEntityTypes;
import sugar.entity.PentuskRenderer;
import sugar.entity.PentuskSpecialRenderer;
import sugar.entity.RedAMDRenderer;
import sugar.entity.BlueAMDRenderer;
import sugar.entity.SugardianRenderer;
import sugar.entity.SweeperRenderer;
import sugar.entity.WindAttackRenderer;
import sugar.menu.DesugarerScreen;
import sugar.menu.FermentationTankScreen;
import sugar.menu.ModMenuTypes;
import sugar.menu.SugarStorageScreen;
import sugar.menu.SugarConverterScreen;

public class SugarClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.DESUGARER, DesugarerScreen::new);
        MenuScreens.register(ModMenuTypes.SUGAR_STORAGE, SugarStorageScreen::new);
        MenuScreens.register(ModMenuTypes.FERMENTATION_TANK, FermentationTankScreen::new);
        MenuScreens.register(ModMenuTypes.SUGAR_CONVERTER, SugarConverterScreen::new);
		ModEntityModelLayers.registerModelLayers();
        EntityRenderers.register(ModEntityTypes.DESERT_DESSERT, DesertDessertRenderer::new);
        EntityRenderers.register(ModEntityTypes.SUGARDIAN, SugardianRenderer::new);
        EntityRenderers.register(ModEntityTypes.BERRY_GOD, BerryGodRenderer::new);
        EntityRenderers.register(ModEntityTypes.PENTUSK, PentuskRenderer::new);
        EntityRenderers.register(ModEntityTypes.HERMIT_DRAGON, HermitDragonRenderer::new);
        EntityRenderers.register(ModEntityTypes.HERMIT_DRAGON_PART, NoopRenderer::new);
        EntityRenderers.register(ModEntityTypes.SWEEPER, SweeperRenderer::new);
        EntityRenderers.register(ModEntityTypes.CANDIED, CandiedRenderer::new);
        EntityRenderers.register(ModEntityTypes.BLACK_HOLE, BlackHoleRenderer::new);
        EntityRenderers.register(ModEntityTypes.WIND_ATTACK, WindAttackRenderer::new);
        EntityRenderers.register(ModEntityTypes.ALLIGANT, AlligantRenderer::new);
        EntityRenderers.register(ModEntityTypes.ALLIGANT_NEST, AlligantNestRenderer::new);
        EntityRenderers.register(ModEntityTypes.RED_AMD, RedAMDRenderer::new);
        EntityRenderers.register(ModEntityTypes.BLUE_AMD, BlueAMDRenderer::new);
        EntityRenderers.register(ModEntityTypes.GNOME, GnomeRenderer::new);
        EntityRenderers.register(ModEntityTypes.ARC, ArcRenderer::new);
        SpecialModelRenderers.ID_MAPPER.put(
            SugarMod.id("pentusk"),
            PentuskSpecialRenderer.Unbaked.MAP_CODEC
        );
        ModAbility.clientInitialize();
        ModBlocks.clientInitialize();
        FirstPersonAnimationRegistry.initialize();
    }
}
