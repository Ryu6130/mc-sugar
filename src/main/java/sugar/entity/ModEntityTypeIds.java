package sugar.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import sugar.SugarMod;

public class ModEntityTypeIds {
	public static ResourceKey<EntityType<?>> DESERT_DESSERT = create("desert_dessert");
	public static ResourceKey<EntityType<?>> SUGARDIAN = create("sugardian");
	public static ResourceKey<EntityType<?>> BERRY_GOD = create("berry_god");
	public static ResourceKey<EntityType<?>> PENTUSK = create("pentusk");

	private static ResourceKey<EntityType<?>> create(String name) {
		return ResourceKey.create(Registries.ENTITY_TYPE, SugarMod.id(name));
	}
}