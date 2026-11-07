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

	private static BlockItemId create(String name) {
		Identifier id = SugarMod.id(name);
		return BlockItemId.create(id, id);
	}
}