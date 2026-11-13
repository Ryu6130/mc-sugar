package sugar.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import sugar.worldgen.ModBiomeIds;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
    @Shadow
    private void addSurfaceBiome(
        Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
        Climate.Parameter temperature,
        Climate.Parameter humidity,
        Climate.Parameter continentalness,
        Climate.Parameter erosion,
        Climate.Parameter weirdness,
        float offset,
        ResourceKey<Biome> biome
    ) {}

    @Inject(method = "addBiomes", at = @At("TAIL"))
    private void sugar$addBiome(
        Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
        CallbackInfo ci
    ) {
        this.addSurfaceBiome(
            biomes,
            Climate.Parameter.span(-0.15f, 0.45f),
            Climate.Parameter.span(-0.1f, 0.5f),
            Climate.Parameter.span(0.2f, 0.7f),
            Climate.Parameter.span(-0.25f, 0.75f),
            Climate.Parameter.span(-0.6f, 0.55f),
            0.0f,
            ModBiomeIds.SUGAR_FOREST
        );
    }
}
