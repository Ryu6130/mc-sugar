package sugar.block;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class ModBlockItemIds {
	public static final BlockItemId SUGAR_CUBE = create("sugar_cube");
	public static final BlockItemId KONPEITO_BLOCK = create("konpeito_block");
	public static final BlockItemId DESUGARER = create("desugarer");
	public static final BlockItemId SUGARRACK = create("sugarrack");
	public static final BlockItemId CANE_LOG = create("cane_log");
	public static final BlockItemId CANE_LEAVES = create("cane_leaves");
	public static final BlockItemId FERMENTATION_TANK = create("fermentation_tank");
	public static final BlockItemId SUGAR_CLOD = create("sugar_clod");
	public static final BlockItemId ARC_EGG = create("arc_egg");
	public static final BlockItemId TWIN_AMD_ALTER = create("twin_amd_alter");
	public static final BlockItemId LOLLIPOP = create("lollipop");
	public static final BlockItemId SUGAR_CONVERTER = create("sugar_converter");

	private static BlockItemId create(String name) {
		Identifier id = SugarMod.id(name);
		return BlockItemId.create(id, id);
	}
}