package sugar.block;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import sugar.SugarMod;
import sugar.item.Lollipop;
import sugar.item.ModItemTags;

public class ModBlocks {
    public static final Block SUGAR_CUBE = register(
		ModBlockItemIds.SUGAR_CUBE,
		SugarCube::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.SAND)
			.strength(0.25f)
			.pushReaction(PushReaction.POPPED)
    );

    public static final Block KONPEITO_BLOCK = register(
		ModBlockItemIds.KONPEITO_BLOCK,
		Block::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.STONE)
			.strength(0.25f)
			.pushReaction(PushReaction.POPPED)
    );
	
    public static final Block DESUGARER = register(
		ModBlockItemIds.DESUGARER,
		Desugarer::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.STONE)
			.strength(2f)
			.pushReaction(PushReaction.IMMOVEABLE)
    );
	
    public static final Block SUGARRACK = register(
		ModBlockItemIds.SUGARRACK,
		Sugarrack::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.NETHERRACK)
			.strength(3f)
    );
	
    public static final Block CANE_LOG = register(
		ModBlockItemIds.CANE_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(MapColor.WOOD, MapColor.PODZOL, SoundType.WOOD),
        new Item.Properties().component(
            DataComponents.COOKING_FUEL,
            new CookingFuel(
                new ResolvableInt.Constant(300),
                ResolvableFloat.fromKey(
                    ContextFloatProviders.COOKING_DEFAULT_SPEED_MULTIPLIER
                )
            )
        )
    );
	
    public static final Block CANE_LEAVES = register(
		ModBlockItemIds.CANE_LEAVES,
		(p) -> new TintedParticleLeavesBlock(0.01f, p),
		Blocks.leavesProperties(SoundType.GRASS)
    );
	
    public static final Block FERMENTATION_TANK = register(
		ModBlockItemIds.FERMENTATION_TANK,
		FermentationTank::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.WOOD)
			.strength(2f)
			.pushReaction(PushReaction.IMMOVEABLE)
    );
	
    public static final Block SUGAR_CLOD = register(
		ModBlockItemIds.SUGAR_CLOD,
		Block::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.GRAVEL)
			.strength(0.5f)
    );
	
    public static final Block ARC_EGG = register(
		ModBlockItemIds.ARC_EGG,
		ArcEgg::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.METAL)
			.strength(0.5f)
			.randomTicks()
			.pushReaction(PushReaction.POPPED)
    );
	
    public static final Block TWIN_AMD_ALTER = register(
		ModBlockItemIds.TWIN_AMD_ALTER,
		TwinAMDAlter::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.METAL)
			.strength(-1f)
			.pushReaction(PushReaction.IMMOVEABLE)
    );

    public static final Block LOLLIPOP = register(
        ModBlockItemIds.LOLLIPOP,
		LollipopBlock::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.GRASS)
			.pushReaction(PushReaction.POPPED)
            .noCollision()
            .noOcclusion()
            .isValidSpawn(Blocks::never)
            .isRedstoneConductor(Blocks::never)
            .isSuffocating(Blocks::never),
        Lollipop::new,
        new Item.Properties().durability(333).attributes(
            ItemAttributeModifiers.builder()
                .add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                        Item.BASE_ATTACK_DAMAGE_ID,
                        11,
                        Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                        Item.BASE_ATTACK_SPEED_ID,
                        -2.9,
                        Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ATTACK_KNOCKBACK,
                    new AttributeModifier(
                        SugarMod.id("base_attack_knockback"),
                        2,
                        Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
                ).build()
        ).component(DataComponents.WEAPON, new Weapon(1)).repairable(ModItemTags.REPAIRS_CANDY)
    );

	private static Block register(
        BlockItemId id,
        Function<BlockBehaviour.Properties, Block> blockFactory,
        BlockBehaviour.Properties properties
    ) {
		Block block = register(id.block(), blockFactory, properties);

		BlockItem blockItem = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(id.item()));
		Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

		return block;
	}

	private static Block register(
        BlockItemId id,
        Function<BlockBehaviour.Properties, Block> blockFactory,
        BlockBehaviour.Properties properties,
		Item.Properties itemProperties
    ) {
		Block block = register(id.block(), blockFactory, properties);

		BlockItem blockItem = new BlockItem(block, itemProperties.useBlockDescriptionPrefix().setId(id.item()));
		Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

		return block;
	}

	private static Block register(
        BlockItemId id,
        Function<BlockBehaviour.Properties, Block> blockFactory,
        BlockBehaviour.Properties properties,
        BiFunction<Block, Item.Properties, BlockItem> itemFactory,
		Item.Properties itemProperties
    ) {
		Block block = register(id.block(), blockFactory, properties);

		BlockItem blockItem = itemFactory.apply(block, itemProperties.useBlockDescriptionPrefix().setId(id.item()));
		Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

		return block;
	}

	private static Block register(
        ResourceKey<Block> id,
        Function<BlockBehaviour.Properties, Block> blockFactory,
        BlockBehaviour.Properties properties
    ) {
		Block block = blockFactory.apply(properties.setId(id));

		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

	public static void clientInitialize() {
        BlockColorRegistry.register(
            List.of(BlockTintSources.foliage()),
            ModBlocks.CANE_LEAVES
        );
	}

    public static void initialize() {
	}
}