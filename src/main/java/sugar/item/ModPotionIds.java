package sugar.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.alchemy.Potion;
import sugar.SugarMod;

public class ModPotionIds {
    public static final ResourceKey<Potion> GAZE_OF_EVIL = register("gaze_of_evil");

    private static ResourceKey<Potion> register(final String name) {
        return ResourceKey.create(Registries.POTION, SugarMod.id(name));
    }

    public static void initialize() {}
}
