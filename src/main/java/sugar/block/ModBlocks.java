package sugar.block;

import java.util.List;
import java.util.function.Function;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import sugar.SugarMod;

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
		Blocks.logProperties(MapColor.WOOD, MapColor.PODZOL, SoundType.WOOD)
    );
	
    public static final Block CANE_LEAVES = register(
		ModBlockItemIds.CANE_LEAVES,
		(p) -> new TintedParticleLeavesBlock(0.01f, p),
		Blocks.leavesProperties(SoundType.GRASS)
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
		BiomeModifications.addFeature(
			BiomeSelectors.includeByKey(
				Biomes.PLAINS,
				Biomes.FOREST,
				Biomes.BIRCH_FOREST,
				Biomes.JUNGLE,
				Biomes.SPARSE_JUNGLE,
				Biomes.BAMBOO_JUNGLE,
				Biomes.SAVANNA,
				Biomes.SAVANNA_PLATEAU,
				Biomes.WINDSWEPT_SAVANNA
			),
			GenerationStep.Decoration.VEGETAL_DECORATION,
			ResourceKey.create(
				Registries.PLACED_FEATURE,
				SugarMod.id("cane_tree")
			)
		);
	}
}