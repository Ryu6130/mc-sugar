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
	public static final ResourceKey<Item> PENTUSK = create("pentusk");
	public static final ResourceKey<Item> WOW_BOW = create("wow_bow");
	public static final ResourceKey<Item> MOLASSES_BOTTLE = create("molasses_bottle");
	public static final ResourceKey<Item> RUM_BOTTLE = create("rum_bottle");
	public static final ResourceKey<Item> NECTAR_BOTTLE = create("nectar_bottle");
	public static final ResourceKey<Item> HERMIT_DRAGON_SCALE = create("hermit_dragon_scale");
	public static final ResourceKey<Item> VOID = create("void");
	public static final ResourceKey<Item> FLAGLESS = create("flagless");
	public static final ResourceKey<Item> SUGAR_WAXED_PEMMICAN = create("sugar_waxed_pemmican");
	public static final ResourceKey<Item> JUST_NIL = create("just_nil");
	public static final ResourceKey<Item> EVEN_LIGHTOO = create("even_lightoo");
	public static final ResourceKey<Item> SWORD_OF_THE_SAGE_REALM = create("sword_of_the_sage_realm");
	public static final ResourceKey<Item> CANDY_SYRUP_BUCKET = create("candy_syrup_bucket");
	public static final ResourceKey<Item> CANDY = create("candy");
	public static final ResourceKey<Item> GELATIN = create("gelatin");
	public static final ResourceKey<Item> GUMMY = create("gummy");
	public static final ResourceKey<Item> GUMMY_BOOTS = create("gummy_boots");
	public static final ResourceKey<Item> GUMMY_CHESTPLATE = create("gummy_chestplate");
	public static final ResourceKey<Item> CANDY_PICKAXE = create("candy_pickaxe");
	public static final ResourceKey<Item> CANDY_CANE = create("candy_cane");
	public static final ResourceKey<Item> ALLIGANT_JAW = create("alligant_jaw");
	public static final ResourceKey<Item> MAGICAL_JAW = create("magical_jaw");
	public static final ResourceKey<Item> SELFISH_LORE = create("selfish_lore");
	public static final ResourceKey<Item> DRAGON_LORE = create("dragon_lore");
	public static final ResourceKey<Item> RAINBOW_BOW = create("rainbow_bow");
	public static final ResourceKey<Item> RED_LORE = create("red_lore");
	public static final ResourceKey<Item> BLUE_LORE = create("blue_lore");
	public static final ResourceKey<Item> ARMORED_KEY = create("armored_key");
	public static final ResourceKey<Item> EXO_ARMOR_SCRAP = create("exo_armor_scrap");
    public static final ResourceKey<Item> PRECISE_SHEARS = create("precise_shears");
    public static final ResourceKey<Item> OMNI_ESSENCE = create("omni_essence");

	public static ResourceKey<Item> create(String name) {
		return ResourceKey.create(Registries.ITEM, SugarMod.id(name));
	}
}
