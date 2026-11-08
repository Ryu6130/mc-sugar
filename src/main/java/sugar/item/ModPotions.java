package sugar.item;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import sugar.effect.ModEffects;

public class ModPotions {
    public static final Holder<Potion> GAZE_OF_EVIL = register(ModPotionIds.GAZE_OF_EVIL,
        new Potion(
            "gaze_of_evil",
            new MobEffectInstance[] {new MobEffectInstance(ModEffects.GAZE_OF_EVIL, 600)}
        )
    );
        

   private static Holder<Potion> register(final ResourceKey<Potion> key, final Potion potion) {
      return Registry.registerForHolder(BuiltInRegistries.POTION, key, potion);
   }

   public static void initialize() {}
}
