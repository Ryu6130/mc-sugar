package sugar.item;

import java.util.function.Function;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import sugar.block.ModBlocks;

public class ModItems {
    public static final Item CARAMEL = register(
        ModItemIds.CARAMEL,
        Item::new,
        new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build())
    );

    public static final Item KONPEITO = register(
        ModItemIds.KONPEITO,
        Item::new,
        new Item.Properties().food(
            new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build(),
            Consumable.builder().consumeSeconds(0.8f).build()
        )
    );

    public static final Item BIOPLASTIC = register(
        ModItemIds.BIOPLASTIC,
        Item::new,
        new Item.Properties()
    );

    public static final Item SUGAR_STORAGE = register(
        ModItemIds.SUGAR_STORAGE,
        SugarStorage::new,
        new Item.Properties().stacksTo(1)
    );

    public static final Item SAND_BERRY = register(
        ModItemIds.SAND_BERRY,
        Item::new,
        new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(1f).build())
    );

    public static final Item SUGAR_CELL = register(
        ModItemIds.SUGAR_CELL,
        SugarCell::new,
        new Item.Properties().durability(20).stacksTo(1)
    );

	public static Item register(
        ResourceKey<Item> itemKey,
        Function<Item.Properties, Item> itemFactory,
        Item.Properties settings
    ) {
		Item item = itemFactory.apply(settings.setId(itemKey));

		Registry.register(BuiltInRegistries.ITEM, itemKey, item);

		return item;
	}

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register((creativeTab) -> {
            creativeTab.accept(ModBlocks.SUGAR_CUBE);
            creativeTab.accept(ModBlocks.SUGARRACK);
            creativeTab.accept(CARAMEL);
            creativeTab.accept(KONPEITO);
            creativeTab.accept(ModBlocks.KONPEITO_BLOCK);
            creativeTab.accept(BIOPLASTIC);
            creativeTab.accept(SUGAR_STORAGE);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register((creativeTab) -> {
            creativeTab.accept(CARAMEL);
            creativeTab.accept(KONPEITO);
            creativeTab.accept(SAND_BERRY);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register((creativeTab) -> {
            creativeTab.accept(ModBlocks.DESUGARER);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register((creativeTab) -> {
            creativeTab.accept(SUGAR_STORAGE);
            creativeTab.accept(SUGAR_CELL);
        });
    }
}