package sugar.entity;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

public class BlackHoleModel extends Model<Unit> {
	public BlackHoleModel(ModelPart root) {
		super(root, RenderTypes::entitySolid);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-4.0F, -4.0F, -4.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-4.0F, -4.0F, 4.0F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(4.0F, -4.0F, -4.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}