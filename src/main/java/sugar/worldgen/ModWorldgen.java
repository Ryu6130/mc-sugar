package sugar.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import sugar.SugarMod;

public class ModWorldgen {
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
				Biomes.WINDSWEPT_SAVANNA,
				ModBiomeIds.SUGAR_FOREST
			),
			GenerationStep.Decoration.VEGETAL_DECORATION,
			ResourceKey.create(
				Registries.PLACED_FEATURE,
				SugarMod.id("cane_tree")
			)
		);
		BiomeModifications.addFeature(
			BiomeSelectors.includeByKey(
				ModBiomeIds.SUGAR_FOREST
			),
			GenerationStep.Decoration.VEGETAL_DECORATION,
			ResourceKey.create(
				Registries.PLACED_FEATURE,
				SugarMod.id("cane_tree_in_sugar")
			)
		);
    }
}
