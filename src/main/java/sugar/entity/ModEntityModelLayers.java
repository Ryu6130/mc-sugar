package sugar.entity;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.creeper.CreeperModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import sugar.SugarMod;

public class ModEntityModelLayers {
	public static final ModelLayerLocation DESERT_DESSERT = createMain("desert_dessert");
	public static final ModelLayerLocation SUGARDIAN = createMain("sugardian");
	public static final ModelLayerLocation BERRY_GOD = createMain("berry_god");
	public static final ModelLayerLocation PENTUSK = createMain("pentusk");
	public static final ModelLayerLocation HERMIT_DRAGON = createMain("hermit_dragon");
	public static final ModelLayerLocation SWEEPER = createMain("sweeper");
	public static final ModelLayerLocation CANDIED = createMain("candied");
	public static final ModelLayerLocation BLACK_HOLE = createMain("black_hole");

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
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SWEEPER,
			() -> CreeperModel.createBodyLayer(CubeDeformation.NONE));
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.CANDIED,
			() -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0), 64, 64));
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BLACK_HOLE,
			BlackHoleModel::createBodyLayer);
	}
}