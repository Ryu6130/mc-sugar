package sugar.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.material.OverworldMaterialRules;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;

import sugar.worldgen.ModMaterialRuleIds;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin {
    @Shadow
    @Final
    private Holder<NoiseGeneratorSettings> settings;

    @ModifyArg(
        method = "buildSurface",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/levelgen/material/MaterialSystem;buildSurface(Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/biome/BiomeManager;Lnet/minecraft/world/level/levelgen/WorldGenerationContext;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/levelgen/NoiseChunk;Lnet/minecraft/world/level/levelgen/material/rule/MaterialRule;Ljava/util/Set;)V"
        ),
        index = 5
    )
    private MaterialRule sugar$addForestRule(MaterialRule originalRule) {
        Holder<MaterialRule> overworldRule = this.settings.value().materialRule();

        if (!overworldRule.is(OverworldMaterialRules.OVERWORLD)) {
            return originalRule;
        }

        @SuppressWarnings("unchecked")
        Registry<MaterialRule> materialRules =
            (Registry<MaterialRule>)((HolderReferenceAccessor<?>) overworldRule).sugar$getOwner();

        MaterialRule sugarRule = materialRules.getOptional(ModMaterialRuleIds.SUGAR_FOREST).get();

        return MaterialRules.sequence(sugarRule, originalRule);
    }
}