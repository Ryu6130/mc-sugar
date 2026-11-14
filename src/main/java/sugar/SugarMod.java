package sugar;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import sugar.block.ModBlocks;
import sugar.blockentity.ModBlockEntityTypes;
import sugar.effect.ModEffects;
import sugar.entity.ModEntitySpawns;
import sugar.entity.ModEntityTypes;
import sugar.item.ModCreativeModeTabs;
import sugar.item.ModDispenserBehaviors;
import sugar.item.ModItems;
import sugar.item.ModPotions;
import sugar.menu.ModMenuTypes;
import sugar.recipe.ModRecipes;
import sugar.worldgen.ModWorldgen;

public class SugarMod implements ModInitializer {
	public static final String MOD_ID = "sugar";

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModBlockEntityTypes.initialize();
        ModMenuTypes.initialize();
        ModRecipes.initialize();
        ModEntityTypes.initialize();
        ModEntitySpawns.initialize();
        ModDispenserBehaviors.initialize();
        ModCreativeModeTabs.initialize();
        ModEffects.initialize();
        ModPotions.initialize();
        ModWorldgen.initialize();
    }

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
