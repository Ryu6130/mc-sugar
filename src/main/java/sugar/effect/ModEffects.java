package sugar.effect;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import sugar.SugarMod;
import sugar.entity.BerryGod;
import sugar.entity.ModEntityTypes;

public class ModEffects {
	public static final Holder<MobEffect> GAZE_OF_EVIL =
        Registry.registerForHolder(
            BuiltInRegistries.MOB_EFFECT,
            SugarMod.id("gaze_of_evil"),
            new GazeOfEvil()
        );

    public static void initialize() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(
            (level, killer, target, source) -> {
                if (
                    killer instanceof LivingEntity living &&
                    living.hasEffect(ModEffects.GAZE_OF_EVIL)
                ) {
                    BerryGod entity = ModEntityTypes.BERRY_GOD.create(level, EntitySpawnReason.TRIGGERED);
                    entity.snapTo(target.position());
                    entity.yBodyRot = target.yBodyRot;
                    level.addFreshEntity(entity);
                    living.removeEffect(ModEffects.GAZE_OF_EVIL);
                }
            }
        );
    }
}