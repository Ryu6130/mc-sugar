package sugar.item;

import java.util.Map;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import sugar.SugarMod;

public class ModMaterials {
    public static final ResourceKey<EquipmentAsset> JET_BOOTS_MATERIAL_KEY =
        ResourceKey.create(EquipmentAssets.ROOT_ID, SugarMod.id("jet_boots"));
    public static final ArmorMaterial JET_BOOTS = new ArmorMaterial(
		31,
		Map.of(
            ArmorType.BOOTS, 3
		),
		5,
		SoundEvents.ARMOR_EQUIP_IRON,
		0.0F,
		0.0F,
		ModItemTags.REPAIRS_JET_BOOTS,
		JET_BOOTS_MATERIAL_KEY
    );
	
    public static final ResourceKey<EquipmentAsset> GUMMY_MATERIAL_KEY =
        ResourceKey.create(EquipmentAssets.ROOT_ID, SugarMod.id("gummy"));
    public static final ArmorMaterial GUMMY = new ArmorMaterial(
		19,
		Map.of(
            ArmorType.BOOTS, 1,
            ArmorType.CHESTPLATE, 4
		),
		6,
		SoundEvents.ARMOR_EQUIP_LEATHER,
		0.0F,
		0.0F,
		ModItemTags.REPAIRS_GUMMY,
		GUMMY_MATERIAL_KEY
    );
	
    public static final ToolMaterial CANDY_TOOL = new ToolMaterial(
		BlockTags.INCORRECT_FOR_GOLD_TOOL, 22, 23.0F, 0.0F, 23, ModItemTags.REPAIRS_CANDY
    );
}
