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

	public static ResourceKey<Item> create(String name) {
		return ResourceKey.create(Registries.ITEM, SugarMod.id(name));
	}
}
