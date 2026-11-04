package sugar.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DesertDessertModel extends EntityModel<DesertDessertRenderState> {
	private final ModelPart head;
	private final ModelPart armr;
	private final ModelPart arml;

	public DesertDessertModel(ModelPart root) {
		super(root);
		this.head = root.getChild(PartNames.HEAD);
		this.armr = this.head.getChild(PartNames.RIGHT_ARM);
		this.arml = this.head.getChild(PartNames.LEFT_ARM);
	}

	@Override
	public void setupAnim(DesertDessertRenderState state) {
		super.setupAnim(state);

		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		float limbSwingAmplitude = state.walkAnimationSpeed;
		float limbSwingAnimationProgress = state.walkAnimationPos;
		this.arml.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
		this.armr.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD,
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-5.0F, -3.0F, -4.0F, 10.0F, 6.0F, 8.0F),
			PartPose.offset(0, -3 + 24, 0)
		);
		head.addOrReplaceChild("berry0",
			CubeListBuilder.create()
				.texOffs(20, 14)
				.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
			PartPose.offset(0, -4, 2)
		);
		head.addOrReplaceChild("berry1",
			CubeListBuilder.create()
				.texOffs(20, 18)
				.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
			PartPose.offset(-3, -4, -1)
		);
		head.addOrReplaceChild("berry2",
			CubeListBuilder.create()
				.texOffs(20, 22)
				.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
			PartPose.offset(3, -4, -1)
		);
		head.addOrReplaceChild(PartNames.RIGHT_ARM,
			CubeListBuilder.create()
				.texOffs(0, 14)
				.addBox(0.0F, -1.0F, -2.0F, 1.0F, 5.0F, 4.0F),
			PartPose.offset(5, -1, 0)
		);
		head.addOrReplaceChild(PartNames.LEFT_ARM,
			CubeListBuilder.create()
				.texOffs(10, 14)
				.addBox(-1.0F, -1.0F, -2.0F, 1.0F, 5.0F, 4.0F),
			PartPose.offset(-5, -1, 0)
		);

		return LayerDefinition.create(modelData, 64, 64);
	}
}