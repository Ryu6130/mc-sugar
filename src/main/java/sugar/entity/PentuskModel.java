package sugar.entity;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;
import sugar.SugarMod;

public class PentuskModel extends Model<Unit> {
	public static final ModelLayerLocation LAYER_LOCATION =
		new ModelLayerLocation(SugarMod.id("pentusk"), "main");
		
	public PentuskModel(ModelPart root) {
		super(root, RenderTypes::entitySolid);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("left_big_spike", CubeListBuilder.create().texOffs(4, 4).addBox(2.5F, -4.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 11).addBox(1.5F, -5.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		partdefinition.addOrReplaceChild("right_big_spike", CubeListBuilder.create().texOffs(10, 4).addBox(-3.5F, -4.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 14).addBox(-2.5F, -5.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 2.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(4, 0).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		partdefinition.addOrReplaceChild("right_spike", CubeListBuilder.create().texOffs(4, 11).addBox(-2.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		partdefinition.addOrReplaceChild("left_spike", CubeListBuilder.create().texOffs(8, 11).addBox(1.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		partdefinition.addOrReplaceChild("middle_spike", CubeListBuilder.create().texOffs(8, 14).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}