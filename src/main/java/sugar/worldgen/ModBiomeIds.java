package sugar.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import sugar.SugarMod;

public class ModBiomeIds {
    public static final ResourceKey<Biome> SUGAR_FOREST = create("sugar_forest");

    public static ResourceKey<Biome> create(String name) {
        return ResourceKey.create(Registries.BIOME, SugarMod.id(name));
    }
}
