package sugar.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

public class AlligantModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart body;
	private final ModelPart left_back_leg;
	private final ModelPart left_front_leg;
	private final ModelPart left_middle_leg;
	private final ModelPart gaster;
	private final ModelPart head;
	private final ModelPart right_jaw;
	private final ModelPart left_jaw;
	private final ModelPart right_back_leg;
	private final ModelPart right_front_leg;
	private final ModelPart right_middle_leg;

	public AlligantModel(ModelPart root) {
		super(root);
		this.body = root.getChild(PartNames.BODY);
		this.left_back_leg = this.body.getChild("left_back_leg");
		this.left_front_leg = this.body.getChild("left_front_leg");
		this.left_middle_leg = this.body.getChild("left_middle_leg");
		this.gaster = this.body.getChild("gaster");
		this.head = this.body.getChild(PartNames.HEAD);
		this.right_jaw = this.head.getChild("right_jaw");
		this.left_jaw = this.head.getChild("left_jaw");
		this.right_back_leg = this.body.getChild("right_back_leg");
		this.right_front_leg = this.body.getChild("right_front_leg");
		this.right_middle_leg = this.body.getChild("right_middle_leg");
	}


	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
	
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		float limbSwingAmplitude = state.walkAnimationSpeed;
		float limbSwingAnimationProgress = state.walkAnimationPos;
		this.left_front_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
		this.left_middle_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;
		this.left_back_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
		this.right_front_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;
		this.right_middle_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
		this.right_back_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;
		this.left_jaw.x = -2f + Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1f * limbSwingAmplitude;
		this.right_jaw.x = 2f + Mth.cos(limbSwingAnimationProgress * 0.2f) * 1f * limbSwingAmplitude;
		this.gaster.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1f * limbSwingAmplitude;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 20).addBox(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

		body.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -1.0F, 4.0F));

		body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -1.0F, -4.0F));

		body.addOrReplaceChild("left_middle_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -1.0F, 0.0F));

		body.addOrReplaceChild("gaster", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 6.0F));

		PartDefinition head = body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, -2.0F, -7.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -6.0F));

		head.addOrReplaceChild("right_jaw", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-2.0F, -1.0F, -3.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 2.0F, -7.0F));

		head.addOrReplaceChild("left_jaw", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -1.0F, -3.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 2.0F, -7.0F));

		body.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -1.0F, 4.0F));

		body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -1.0F, -4.0F));

		body.addOrReplaceChild("right_middle_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}