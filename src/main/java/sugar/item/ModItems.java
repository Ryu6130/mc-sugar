package sugar.item;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.TeleportRandomlyConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;

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

    public static final Item SUGARITE_INGOT = register(
        ModItemIds.SUGARITE_INGOT,
        Item::new,
        new Item.Properties()
    );

    public static final Item JET_BOOTS = register(
        ModItemIds.JET_BOOTS,
        Item::new,
        new Item.Properties().humanoidArmor(ModArmors.JET_BOOTS, ArmorType.BOOTS)
            .durability(ArmorType.HELMET.getDurability(ModArmors.JET_BOOTS.durability()))
    );

    public static final Item ENDER_BERRIES = register(
        ModItemIds.ENDER_BERRIES,
        Item::new,
        new Item.Properties().stacksTo(16).food(
            new FoodProperties.Builder().nutrition(1).saturationModifier(1f).build(),
            Consumables.CHORUS_FRUIT
        ).useCooldown(0.5f)
    );

    public static final Item THE_END_SUPPER = register(
        ModItemIds.THE_END_SUPPER,
        Item::new,
        new Item.Properties().stacksTo(1).food(
            new FoodProperties.Builder().nutrition(5).saturationModifier(1.1f).build(),
            Consumables.defaultDrink()
                .onConsume(new TeleportRandomlyConsumeEffect(32, true)).build()
        ).usingConvertsTo(Items.BOWL)
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
        JetManager.initialize();
    }
}