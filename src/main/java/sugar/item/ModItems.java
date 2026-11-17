package sugar.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.item.equipment.ArmorType;
import sugar.SugarMod;
import sugar.component.InventoryAttributesComponent;
import sugar.component.ModDataComponents;
import sugar.component.SimpleTooltipComponent;

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
            .durability(ArmorType.BOOTS.getDurability(ModMaterials.JET_BOOTS.durability()))
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
        new Item.Properties().durability(333).enchantable(17).attributes(
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
                        -2.2,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).build()
        ).repairable(VOID)
    );

    public static final Item SUGAR_WAXED_PEMMICAN = register(
        ModItemIds.SUGAR_WAXED_PEMMICAN,
        Item::new,
        new Item.Properties().food(
            new FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).build(),
            Consumables.defaultFood().consumeSeconds(1.2f).build()
        )
    );

    public static final Item JUST_NIL = register(
        ModItemIds.JUST_NIL,
        Item::new,
        new Item.Properties().durability(333).enchantable(17).attributes(
            ItemAttributeModifiers.builder()
                .add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                        Item.BASE_ATTACK_DAMAGE_ID,
                        4.5,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                        Item.BASE_ATTACK_SPEED_ID,
                        -1.9,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ENTITY_INTERACTION_RANGE,
                    new AttributeModifier(
                        SugarMod.id("base_entity_reach"),
                        2,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).build()
        ).component(DataComponents.WEAPON, new Weapon(1)).repairable(VOID)
    );

    public static final Item EVEN_LIGHTOO = register(
        ModItemIds.EVEN_LIGHTOO,
        EvenLightoo::new,
        new Item.Properties().durability(245).enchantable(17).repairable(VOID)
    );

    public static final Item SWORD_OF_THE_SAGE_REALM = register(
        ModItemIds.SWORD_OF_THE_SAGE_REALM,
        Item::new,
        new Item.Properties().durability(456).enchantable(16).attributes(
            ItemAttributeModifiers.builder()
                .add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                        Item.BASE_ATTACK_DAMAGE_ID,
                        5.5,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                        Item.BASE_ATTACK_SPEED_ID,
                        -0.7,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).build()
        ).component(DataComponents.WEAPON, new Weapon(1)).repairable(HERMIT_DRAGON_SCALE)
        .component(DataComponents.TOOL, ModTools.SWORD)
    );

    public static final Item CANDY_SYRUP_BUCKET = register(
        ModItemIds.CANDY_SYRUP_BUCKET,
        Item::new,
        new Item.Properties().stacksTo(1).food(
            new FoodProperties.Builder()
                .nutrition(2)
                .saturationModifier(1.5f)
                .alwaysEdible()
                .build(),
            Consumables.defaultDrink()
                .onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(MobEffects.SLOWNESS, 100, 1), 1)
                ).onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0), 1)
                ).build()
        ).usingConvertsTo(Items.BUCKET).craftRemainder(Items.BUCKET)
    );

    public static final Item CANDY = register(
        ModItemIds.CANDY,
        Item::new,
        new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(3)
                .saturationModifier(1.2f)
                .build()
        )
    );

    public static final Item GELATIN = register(
        ModItemIds.GELATIN,
        Item::new,
        new Item.Properties()
    );

    public static final Item GUMMY = register(
        ModItemIds.GUMMY,
        Item::new,
        new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(3)
                .saturationModifier(1.5f)
                .build()
        )
    );

    public static final Item GUMMY_BOOTS = register(
        ModItemIds.GUMMY_BOOTS,
        Item::new,
        new Item.Properties().humanoidArmor(ModMaterials.GUMMY, ArmorType.BOOTS).attributes(
            ItemAttributeModifiers.builder()
                .add(
                    Attributes.ARMOR,
                    new AttributeModifier(
                        Identifier.withDefaultNamespace("armor.boots"),
                        1,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.FEET
                ).add(
                    Attributes.FALL_DAMAGE_MULTIPLIER,
                    new AttributeModifier(
                        Identifier.withDefaultNamespace("armor.boots"),
                        -0.95,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.FEET
                ).build()
        ).durability(ArmorType.BOOTS.getDurability(ModMaterials.GUMMY.durability()))
    );

    public static final Item GUMMY_CHESTPLATE = register(
        ModItemIds.GUMMY_CHESTPLATE,
        Item::new,
        new Item.Properties().humanoidArmor(ModMaterials.GUMMY, ArmorType.CHESTPLATE).attributes(
            ItemAttributeModifiers.builder()
                .add(
                    Attributes.ARMOR,
                    new AttributeModifier(
                        Identifier.withDefaultNamespace("armor.chestplate"),
                        4,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.CHEST
                ).add(
                    Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(
                        Identifier.withDefaultNamespace("armor.chestplate"),
                        0.8,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.CHEST
                ).build()
        ).durability(ArmorType.CHESTPLATE.getDurability(ModMaterials.GUMMY.durability()))
    );
    
    public static final Item CANDY_PICKAXE = register(
        ModItemIds.CANDY_PICKAXE,
        Item::new,
        new Item.Properties().pickaxe(ModMaterials.CANDY_TOOL, 1, -2.8f)
    );

    public static final Item CANDY_CANE = register(
        ModItemIds.CANDY_CANE,
        Item::new,
        new Item.Properties().durability(234).enchantable(14).attributes(
            ItemAttributeModifiers.builder()
                .add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                        Item.BASE_ATTACK_DAMAGE_ID,
                        6,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                        Item.BASE_ATTACK_SPEED_ID,
                        -2.0,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ENTITY_INTERACTION_RANGE,
                    new AttributeModifier(
                        SugarMod.id("base_entity_reach"),
                        0.5,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).build()
        ).component(DataComponents.WEAPON, new Weapon(1)).repairable(CANDY)
    );
    
    public static final Item ALLIGANT_JAW = register(
        ModItemIds.ALLIGANT_JAW,
        Item::new,
        new Item.Properties()
    );
    
    public static final Item MAGICAL_JAW = register(
        ModItemIds.MAGICAL_JAW,
        Item::new,
        new Item.Properties().stacksTo(1).attributes(
            ItemAttributeModifiers.builder()
                .add(
                    Attributes.BLOCK_INTERACTION_RANGE,
                    new AttributeModifier(
                        ModItemIds.MAGICAL_JAW.identifier(),
                        3,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.HAND
                ).add(
                    Attributes.ENTITY_INTERACTION_RANGE,
                    new AttributeModifier(
                        ModItemIds.MAGICAL_JAW.identifier(),
                        1,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.HAND
                ).build()
        )
    );
    
    public static final Item SELFISH_LORE = register(
        ModItemIds.SELFISH_LORE,
        Item::new,
        new Item.Properties().stacksTo(1).component(
            ModDataComponents.INVENTORY_ATTRIBUTES,
            InventoryAttributesComponent.builder()
                .add(
                    Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(
                        ModItemIds.SELFISH_LORE.identifier(),
                        0.01,
                        AttributeModifier.Operation.ADD_VALUE
                    )
                ).add(
                    Attributes.SNEAKING_SPEED,
                    new AttributeModifier(
                        ModItemIds.SELFISH_LORE.identifier(),
                        0.005,
                        AttributeModifier.Operation.ADD_VALUE
                    )
                ).build()
        ).component(
            ModDataComponents.SIMPLE_TOOLTIP,
            new SimpleTooltipComponent(List.of(
                Component.translatable("itemTooltip.sugar.selfish_lore.line1"),
                Component.translatable("itemTooltip.sugar.selfish_lore.line2"),
                Component.translatable("itemTooltip.sugar.selfish_lore.line3"),
                Component.translatable("itemTooltip.sugar.selfish_lore.line4"),
                Component.translatable("itemTooltip.sugar.selfish_lore.line5"),
                Component.translatable("itemTooltip.sugar.selfish_lore.line6"),
                Component.translatable("itemTooltip.sugar.selfish_lore.line7"),
                Component.translatable("itemTooltip.sugar.selfish_lore.line8"),
                Component.translatable("itemTooltip.sugar.selfish_lore.line9")
            ))
        )
    );
    
    public static final Item DRAGON_LORE = register(
        ModItemIds.DRAGON_LORE,
        Item::new,
        new Item.Properties().stacksTo(1).component(
            ModDataComponents.INVENTORY_ATTRIBUTES,
            InventoryAttributesComponent.builder()
                .add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                        SugarMod.id("base_attack_damage"),
                        0.5,
                        AttributeModifier.Operation.ADD_VALUE
                    )
                ).add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                        SugarMod.id("base_attack_speed"),
                        -0.2,
                        AttributeModifier.Operation.ADD_VALUE
                    )
                ).build()
        ).component(
            ModDataComponents.SIMPLE_TOOLTIP,
            new SimpleTooltipComponent(List.of(
                Component.translatable("itemTooltip.sugar.dragon_lore.line1"),
                Component.translatable("itemTooltip.sugar.dragon_lore.line2"),
                Component.translatable("itemTooltip.sugar.dragon_lore.line3"),
                Component.translatable("itemTooltip.sugar.dragon_lore.line4")
            ))
        )
    );

    public static final Item RAINBOW_BOW = register(
        ModItemIds.RAINBOW_BOW,
        RainbowBow::new,
        new Item.Properties()
            .durability(455)
            .enchantable(3)
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
        DefaultItemComponentEvents.MODIFY.register(context -> {
            List<Holder<Item>> items = new ArrayList<>(
                Items.ELYTRA.components().get(DataComponents.REPAIRABLE)
                    .items().stream().toList()
            );
            items.add(BuiltInRegistries.ITEM.wrapAsHolder(HERMIT_DRAGON_SCALE));

            context.modify(Items.ELYTRA, builder -> {
                builder.set(
                    DataComponents.REPAIRABLE,
                    new Repairable(HolderSet.direct(items))
                );
            });
        });
    }
}