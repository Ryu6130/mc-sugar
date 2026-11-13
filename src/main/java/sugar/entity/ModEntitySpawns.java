package sugar.entity;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import sugar.worldgen.ModBiomeIds;

public class ModEntitySpawns {
    public static void initialize() {
        SpawnPlacements.register(
            ModEntityTypes.DESERT_DESSERT,
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            DesertDessert::checkDesertDessertSpawnRules
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(Biomes.DESERT),
            MobCategory.MONSTER,
            ModEntityTypes.DESERT_DESSERT,
            50,
            1,
            1 
        );
        SpawnPlacements.register(
            ModEntityTypes.HERMIT_DRAGON,
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            HermitDragon::checkSpawnRules
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(
                Biomes.WINDSWEPT_HILLS,
                Biomes.WINDSWEPT_GRAVELLY_HILLS,
                Biomes.WINDSWEPT_FOREST,
                Biomes.WINDSWEPT_SAVANNA,
                Biomes.MEADOW,
                Biomes.STONY_PEAKS,
                Biomes.JAGGED_PEAKS
            ),
            MobCategory.MONSTER,
            ModEntityTypes.HERMIT_DRAGON,
            2,
            1,
            1 
        );
        SpawnPlacements.register(
            ModEntityTypes.SWEEPER,
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Sweeper::checkSpawnRules
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(
                ModBiomeIds.SUGAR_FOREST
            ),
            MobCategory.MONSTER,
            ModEntityTypes.SWEEPER,
            50,
            1,
            3
        );
        SpawnPlacements.register(
            ModEntityTypes.CANDIED,
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Candied::checkSpawnRules
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(
                ModBiomeIds.SUGAR_FOREST
            ),
            MobCategory.MONSTER,
            ModEntityTypes.CANDIED,
            80,
            1,
            4
        );
    }
}
