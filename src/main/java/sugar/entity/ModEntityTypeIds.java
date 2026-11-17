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
	public static ResourceKey<EntityType<?>> HERMIT_DRAGON = create("hermit_dragon");
	public static ResourceKey<EntityType<?>> HERMIT_DRAGON_PART = create("hermit_dragon_part");
	public static ResourceKey<EntityType<?>> SWEEPER = create("sweeper");
	public static ResourceKey<EntityType<?>> CANDIED = create("candied");
	public static ResourceKey<EntityType<?>> BLACK_HOLE = create("black_hole");
	public static ResourceKey<EntityType<?>> WIND_ATTACK = create("wind_attack");
	public static ResourceKey<EntityType<?>> ALLIGANT = create("alligant");
	public static ResourceKey<EntityType<?>> ALLIGANT_NEST = create("alligant_nest");
	public static ResourceKey<EntityType<?>> RED_AMD = create("red_amd");
	public static ResourceKey<EntityType<?>> BLUE_AMD = create("blue_amd");

	private static ResourceKey<EntityType<?>> create(String name) {
		return ResourceKey.create(Registries.ENTITY_TYPE, SugarMod.id(name));
	}
}