package sugar.item;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;
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
        new Item.Properties().humanoidArmor(ModMaterials.JET_BOOTS, ArmorType.BOOTS)
            .durability(ArmorType.HELMET.getDurability(ModMaterials.JET_BOOTS.durability()))
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
            Consumables.defaultFood()
                .onConsume(new TeleportRandomlyConsumeEffect(32, true)).build()
        ).usingConvertsTo(Items.BOWL)
    );

    public static final Item SCULK_BERRIES = register(
        ModItemIds.SCULK_BERRIES,
        Item::new,
        new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(1f).build())
    );

    public static final Item FLAME_BERRIES = register(
        ModItemIds.FLAME_BERRIES,
        FlameBerries::new,
        new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(1f).build())
    );

    public static final Item FROSTED_BERRIES = register(
        ModItemIds.FROSTED_BERRIES,
        Item::new,
        new Item.Properties().food(
            new FoodProperties.Builder().nutrition(5).saturationModifier(1f).build(),
            Consumables.defaultFood().onConsume(
                new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.SLOWNESS, 60, 1), 1)
                ).build()
        )
    );

    public static final Item OCEAN_BERRIES = register(
        ModItemIds.OCEAN_BERRIES,
        Item::new,
        new Item.Properties().food(
            new FoodProperties.Builder().nutrition(2).saturationModifier(1.3f).build(),
            Consumables.defaultFood().onConsume(
                new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 1), 1)
                ).build()
        )
    );

    public static final Item RAINBOW_SOUP = register(
        ModItemIds.RAINBOW_SOUP,
        Item::new,
        new Item.Properties().stacksTo(1).food(
            new FoodProperties.Builder().nutrition(10).saturationModifier(0.8f).build(),
            Consumables.defaultDrink().build()
        ).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL)
    );

    public static final Item SWEETEST_BERRIES = register(
        ModItemIds.SWEETEST_BERRIES,
        Item::new,
        new Item.Properties().food(
            new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build(),
            Consumables.defaultFood().onConsume(
                new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1)
                ).build()
        )
    );

    public static final Item PENTUSK = register(
        ModItemIds.PENTUSK,
        Pentusk::new,
        new Item.Properties()
            .durability(310)
            .attributes(Pentusk.createAttributes())
            .component(DataComponents.TOOL, Pentusk.createToolProperties())
            .enchantable(1)
            .component(DataComponents.WEAPON, new Weapon(1))
    );

    public static final Item WOW_BOW = register(
        ModItemIds.WOW_BOW,
        WowBow::new,
        new Item.Properties()
            .durability(411)
            .enchantable(1)
    );

    public static final Item MOLASSES_BOTTLE = register(
        ModItemIds.MOLASSES_BOTTLE,
        Item::new,
        new Item.Properties().stacksTo(1).food(
            new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).alwaysEdible().build(),
            Consumables.defaultDrink().consumeSeconds(2.0F).sound(SoundEvents.HONEY_DRINK)
                .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.WITHER)).build()
        ).usingConvertsTo(Items.GLASS_BOTTLE).craftRemainder(Items.GLASS_BOTTLE)
    );

    public static final Item RUM_BOTTLE = register(
        ModItemIds.RUM_BOTTLE,
        Item::new,
        new Item.Properties().stacksTo(1).food(
            new FoodProperties.Builder().nutrition(0).alwaysEdible().build(),
            Consumables.defaultDrink().consumeSeconds(2.0F).sound(SoundEvents.HONEY_DRINK)
                .onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(MobEffects.NAUSEA, 120, 0), 1)
                ).build()
        ).usingConvertsTo(Items.GLASS_BOTTLE).craftRemainder(Items.GLASS_BOTTLE)
    );

    public static final Item NECTAR_BOTTLE = register(
        ModItemIds.NECTAR_BOTTLE,
        Item::new,
        new Item.Properties().stacksTo(1).food(
            new FoodProperties.Builder().nutrition(7).saturationModifier(0.6f).alwaysEdible().build(),
            Consumables.defaultDrink().consumeSeconds(2.0F).sound(SoundEvents.HONEY_DRINK)
                .onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(MobEffects.REGENERATION, 160, 1), 1)
                ).onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(MobEffects.ABSORPTION, 320, 2), 1)
                ).onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0), 1)
                ).onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(MobEffects.NIGHT_VISION, 1000, 1), 1)
                ).build()
        ).usingConvertsTo(Items.GLASS_BOTTLE).craftRemainder(Items.GLASS_BOTTLE)
    );

    public static final Item HERMIT_DRAGON_SCALE = register(
        ModItemIds.HERMIT_DRAGON_SCALE,
        Item::new,
        new Item.Properties()
    );

    public static final Item VOID = register(
        ModItemIds.VOID,
        Item::new,
        new Item.Properties()
    );

    public static final Item FLAGLESS = register(
        ModItemIds.FLAGLESS,
        Item::new,
        new Item.Properties().durability(1111).attributes(
            ItemAttributeModifiers.builder()
                .add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                        Item.BASE_ATTACK_DAMAGE_ID,
                        3.0,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                        Item.BASE_ATTACK_SPEED_ID,
                        -2.4,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).build()
        )
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
        MultiAttackManager.initialize();
    }
}