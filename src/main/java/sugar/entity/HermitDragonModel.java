package sugar.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class HermitDragonModel extends EntityModel<HermitDragonRenderState> {
	private final ModelPart body;
	private final ModelPart back_left_leg;
	private final ModelPart back_left_shin;
	private final ModelPart back_left_foot;
	private final ModelPart back_right_leg;
	private final ModelPart back_right_shin;
	private final ModelPart back_right_foot;
	private final ModelPart front_left_leg;
	private final ModelPart front_left_shin;
	private final ModelPart front_left_foot;
	private final ModelPart front_right_leg;
	private final ModelPart front_right_shin;
	private final ModelPart front_right_foot;
	private final ModelPart neck1;
	private final ModelPart neck2;
	private final ModelPart neck3;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart left_wing;
	private final ModelPart left_wing_tip;
	private final ModelPart right_wing;
	private final ModelPart right_wing_tip;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	// private final ModelPart tail5;

	@Override
	public void setupAnim(HermitDragonRenderState state) {
		super.setupAnim(state);

		this.neck1.xRot = state.xRot * Mth.DEG_TO_RAD / 4;
		this.neck1.yRot = state.yRot * Mth.DEG_TO_RAD / 4;
		this.neck2.xRot = state.xRot * Mth.DEG_TO_RAD / 4;
		this.neck2.yRot = state.yRot * Mth.DEG_TO_RAD / 4;
		this.neck3.xRot = state.xRot * Mth.DEG_TO_RAD / 4;
		this.neck3.yRot = state.yRot * Mth.DEG_TO_RAD / 4;
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD / 4;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD / 4;
		float limbSwingAmplitude = state.walkAnimationSpeed;
		float limbSwingAnimationProgress = state.walkAnimationPos;
		this.left_wing.zRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.2f * limbSwingAmplitude;
		this.right_wing.zRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.2f * limbSwingAmplitude;
		this.left_wing_tip.zRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.2f * limbSwingAmplitude;
		this.right_wing_tip.zRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.2f * limbSwingAmplitude;
		this.back_left_leg.xRot = Mth.PI / -4f + Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.2f * limbSwingAmplitude;
		this.back_right_leg.xRot = Mth.PI / -4f + Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.2f * limbSwingAmplitude;
		this.back_left_shin.xRot = Mth.PI / 2f + Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.05f * limbSwingAmplitude;
		this.back_right_shin.xRot = Mth.PI / 2f + Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.05f * limbSwingAmplitude;
		this.back_left_foot.xRot = Mth.PI / -4f + Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.02f * limbSwingAmplitude;
		this.back_right_foot.xRot = Mth.PI / -4f + Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.02f * limbSwingAmplitude;
		this.front_left_leg.xRot = Mth.PI / -8f + Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.2f * limbSwingAmplitude;
		this.front_right_leg.xRot = Mth.PI / -8f + Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.2f * limbSwingAmplitude;
		this.front_left_shin.xRot = Mth.PI / 8f + Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.05f * limbSwingAmplitude;
		this.front_right_shin.xRot = Mth.PI / 8f + Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.05f * limbSwingAmplitude;
		this.front_left_foot.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.02f * limbSwingAmplitude;
		this.front_right_foot.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.02f * limbSwingAmplitude;
		this.body.y = -20;
		this.jaw.xRot = state.jawRot;
	}

	public HermitDragonModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.back_left_leg = this.body.getChild("back_left_leg");
		this.back_left_shin = this.back_left_leg.getChild("back_left_shin");
		this.back_left_foot = this.back_left_shin.getChild("back_left_foot");
		this.back_right_leg = this.body.getChild("back_right_leg");
		this.back_right_shin = this.back_right_leg.getChild("back_right_shin");
		this.back_right_foot = this.back_right_shin.getChild("back_right_foot");
		this.front_left_leg = this.body.getChild("front_left_leg");
		this.front_left_shin = this.front_left_leg.getChild("front_left_shin");
		this.front_left_foot = this.front_left_shin.getChild("front_left_foot");
		this.front_right_leg = this.body.getChild("front_right_leg");
		this.front_right_shin = this.front_right_leg.getChild("front_right_shin");
		this.front_right_foot = this.front_right_shin.getChild("front_right_foot");
		this.neck1 = this.body.getChild("neck1");
		this.neck2 = this.neck1.getChild("neck2");
		this.neck3 = this.neck2.getChild("neck3");
		this.head = this.neck3.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.left_wing = this.body.getChild("left_wing");
		this.left_wing_tip = this.left_wing.getChild("left_wing_tip");
		this.right_wing = this.body.getChild("right_wing");
		this.right_wing_tip = this.right_wing.getChild("right_wing_tip");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail4 = this.tail3.getChild("tail4");
		/*this.tail5 = */this.tail4.getChild("tail5");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -16.0F, -35.0F, 36.0F, 28.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(0, 98).addBox(-4.0F, -24.0F, -31.0F, 8.0F, 8.0F, 62.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -34.0F, 0.0F));

		PartDefinition back_left_leg = body.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -6.0F, -7.0F, 12.0F, 28.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, 4.0F, 19.0F));

		PartDefinition back_left_shin = back_left_leg.addOrReplaceChild("back_left_shin", CubeListBuilder.create().texOffs(96, 168).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 26.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 0.0F));

		back_left_shin.addOrReplaceChild("back_left_foot", CubeListBuilder.create().texOffs(78, 118).addBox(-8.0F, 0.0F, -20.0F, 16.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 1.0F));

		PartDefinition back_right_leg = body.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-6.0F, -6.0F, -7.0F, 12.0F, 28.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.0F, 4.0F, 19.0F));

		PartDefinition back_right_shin = back_right_leg.addOrReplaceChild("back_right_shin", CubeListBuilder.create().texOffs(96, 168).mirror().addBox(-5.0F, 0.0F, -5.0F, 10.0F, 26.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 22.0F, 0.0F));

		back_right_shin.addOrReplaceChild("back_right_foot", CubeListBuilder.create().texOffs(78, 118).mirror().addBox(-8.0F, 0.0F, -20.0F, 16.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 26.0F, 1.0F));

		PartDefinition front_left_leg = body.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 98).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, 6.0F, -15.0F));

		PartDefinition front_left_shin = front_left_leg.addOrReplaceChild("front_left_shin", CubeListBuilder.create().texOffs(64, 168).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		front_left_shin.addOrReplaceChild("front_left_foot", CubeListBuilder.create().texOffs(0, 42).addBox(-5.0F, 0.0F, -12.0F, 10.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition front_right_leg = body.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(0, 98).mirror().addBox(-6.0F, -4.0F, -6.0F, 12.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.0F, 6.0F, -15.0F));

		PartDefinition front_right_shin = front_right_leg.addOrReplaceChild("front_right_shin", CubeListBuilder.create().texOffs(64, 168).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 16.0F, 0.0F));

		front_right_shin.addOrReplaceChild("front_right_foot", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-5.0F, 0.0F, -12.0F, 10.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition neck1 = body.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(0, 130).addBox(-6.0F, -6.0F, -10.0F, 12.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -35.0F));

		PartDefinition neck2 = neck1.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(142, 36).addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

		PartDefinition neck3 = neck2.addOrReplaceChild("neck3", CubeListBuilder.create().texOffs(0, 130).addBox(-6.0F, -6.0F, -10.0F, 12.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

		PartDefinition head = neck3.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 168).addBox(-8.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(142, 0).addBox(-6.0F, 0.0F, -30.0F, 12.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(78, 118).mirror().addBox(-11.0F, -14.0F, -10.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(78, 118).addBox(7.0F, -14.0F, -10.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -10.0F));

		head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(142, 18).addBox(-6.0F, 0.0F, -14.0F, 12.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -16.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(-48, 200).addBox(0.0F, -1.0F, 3.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F))
		.texOffs(78, 98).mirror().addBox(0.0F, -4.0F, -3.0F, 48.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(18.0F, -15.0F, -20.0F));

		left_wing.addOrReplaceChild("left_wing_tip", CubeListBuilder.create().texOffs(78, 110).addBox(0.0F, -3.0F, -2.0F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(-48, 200).mirror().addBox(0.0F, -1.0F, 2.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(48.0F, 0.0F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(78, 98).addBox(-48.0F, -4.0F, -3.0F, 48.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-48, 200).mirror().addBox(-48.0F, -1.0F, 3.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-18.0F, -15.0F, -20.0F));

		right_wing.addOrReplaceChild("right_wing_tip", CubeListBuilder.create().texOffs(78, 110).addBox(-48.0F, -3.0F, -2.0F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(-48, 200).addBox(-48.0F, -1.0F, 2.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(-48.0F, 0.0F, 0.0F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 130).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 35.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(142, 36).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 130).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

		PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(142, 36).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

		tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(134, 118).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}
}