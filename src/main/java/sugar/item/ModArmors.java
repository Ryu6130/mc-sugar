package sugar.item;

import java.util.Map;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import sugar.SugarMod;

public class ModArmors {
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
		TagKey.create(Registries.ITEM, SugarMod.id("repairs_jet_boots")),
		JET_BOOTS_MATERIAL_KEY
    );
}
