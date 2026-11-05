package sugar.block;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class ModBlockItemIds {
	public static final BlockItemId SUGAR_CUBE = create("sugar_cube");
	public static final BlockItemId KONPEITO_BLOCK = create("konpeito_block");
	public static final BlockItemId DESUGARER = create("desugarer");
	public static final BlockItemId SUGARRACK = create("sugarrack");

	private static BlockItemId create(String name) {
		Identifier id = SugarMod.id(name);
		return BlockItemId.create(id, id);
	}
}