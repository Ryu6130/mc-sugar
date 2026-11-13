package sugar.item;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import sugar.SugarMod;
import sugar.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final ResourceKey<CreativeModeTab> SUGAR_CREATIVE_TAB_KEY = ResourceKey.create(
		BuiltInRegistries.CREATIVE_MODE_TAB.key(), SugarMod.id("creative_tab")
    );
    public static final CreativeModeTab SUGAR_CREATIVE_TAB = FabricCreativeModeTab.builder()
        .icon(() -> new ItemStack(Items.SUGAR))
        .title(Component.translatable("creativeTab.sugar"))
        .displayItems((params, output) -> {
            output.accept(ModBlocks.SUGAR_CUBE);
            output.accept(ModBlocks.SUGAR_CLOD);
            output.accept(ModBlocks.SUGARRACK);
            output.accept(ModItems.SUGARITE_INGOT);

            output.accept(ModItems.CARAMEL);
            output.accept(ModItems.KONPEITO);
            output.accept(ModBlocks.KONPEITO_BLOCK);
            output.accept(ModItems.MOLASSES_BOTTLE);
            output.accept(ModItems.RUM_BOTTLE);
            output.accept(ModItems.NECTAR_BOTTLE);
            
            output.accept(ModItems.VOID);
            output.accept(ModItems.FLAGLESS);

            output.accept(ModItems.HERMIT_DRAGON_SCALE);

            output.accept(ModItems.SAND_BERRY);
            output.accept(ModItems.FROSTED_BERRIES);
            output.accept(ModItems.OCEAN_BERRIES);
            output.accept(ModItems.SCULK_BERRIES);
            output.accept(ModItems.FLAME_BERRIES);
            output.accept(ModItems.ENDER_BERRIES);
            output.accept(ModItems.THE_END_SUPPER);
            output.accept(ModItems.RAINBOW_SOUP);
            output.accept(ModItems.SWEETEST_BERRIES);
            output.accept(PotionContents.createItemStack(Items.POTION, ModPotions.GAZE_OF_EVIL));
            
            output.accept(ModItems.BIOPLASTIC);
            output.accept(ModItems.SUGAR_STORAGE);
            output.accept(ModItems.SUGAR_CELL);

            output.accept(ModBlocks.DESUGARER);
            output.accept(ModBlocks.FERMENTATION_TANK);

            output.accept(ModItems.WOW_BOW);
            output.accept(ModItems.JET_BOOTS);
            output.accept(ModItems.PENTUSK);

            output.accept(ModBlocks.CANE_LOG);
            output.accept(ModBlocks.CANE_LEAVES);
        })
        .build();

    public static void initialize() {
        Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            SUGAR_CREATIVE_TAB_KEY,
            SUGAR_CREATIVE_TAB
        );
    }
}
