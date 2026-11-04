package sugar.entity;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

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
    }
}
