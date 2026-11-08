package sugar.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import sugar.SugarMod;

public class ModItemIds {
	public static final ResourceKey<Item> CARAMEL = create("caramel");
	public static final ResourceKey<Item> KONPEITO = create("konpeito");
	public static final ResourceKey<Item> BIOPLASTIC = create("bioplastic");
	public static final ResourceKey<Item> SUGAR_STORAGE = create("sugar_storage");
	public static final ResourceKey<Item> SAND_BERRY = create("sand_berry");
	public static final ResourceKey<Item> SUGAR_CELL = create("sugar_cell");
	public static final ResourceKey<Item> SUGARITE_INGOT = create("sugarite_ingot");
	public static final ResourceKey<Item> JET_BOOTS = create("jet_boots");
	public static final ResourceKey<Item> ENDER_BERRIES = create("ender_berries");
	public static final ResourceKey<Item> THE_END_SUPPER = create("the_end_supper");
	public static final ResourceKey<Item> SCULK_BERRIES = create("sculk_berries");
	public static final ResourceKey<Item> FLAME_BERRIES = create("flame_berries");
	public static final ResourceKey<Item> FROSTED_BERRIES = create("frosted_berries");
	public static final ResourceKey<Item> OCEAN_BERRIES = create("ocean_berries");
	public static final ResourceKey<Item> RAINBOW_SOUP = create("rainbow_soup");
	public static final ResourceKey<Item> SWEETEST_BERRIES = create("sweetest_berries");

	public static ResourceKey<Item> create(String name) {
		return ResourceKey.create(Registries.ITEM, SugarMod.id(name));
	}
}
