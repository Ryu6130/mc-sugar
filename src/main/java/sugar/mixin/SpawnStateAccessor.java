package sugar.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.NaturalSpawner;

@Mixin (NaturalSpawner.SpawnState.class)
public interface SpawnStateAccessor {
    @Accessor ("localMobCapCalculator")
    LocalMobCapCalculator sugar$getLocalMobCapCalculator();
}