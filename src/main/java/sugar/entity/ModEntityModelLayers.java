package sugar.entity;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import sugar.SugarMod;

public class ModEntityModelLayers {
	public static final ModelLayerLocation DESERT_DESSERT = createMain("desert_dessert");
	public static final ModelLayerLocation SUGARDIAN = createMain("sugardian");
	public static final ModelLayerLocation BERRY_GOD = createMain("berry_god");
	public static final ModelLayerLocation PENTUSK = createMain("pentusk");
	public static final ModelLayerLocation HERMIT_DRAGON = createMain("hermit_dragon");

	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(SugarMod.id(name), "main");
	}

	public static void registerModelLayers() {
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.DESERT_DESSERT,
			DesertDessertModel::getTexturedModelData);
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SUGARDIAN,
			SugardianModel::getTexturedModelData);
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BERRY_GOD,
			BerryGodModel::getTexturedModelData);
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.PENTUSK,
			PentuskModel::getTexturedModelData);
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.HERMIT_DRAGON,
			HermitDragonModel::getTexturedModelData);
	}
}