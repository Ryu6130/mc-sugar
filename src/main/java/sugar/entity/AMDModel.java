package sugar.entity;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class AMDModel extends EntityModel<AMDRenderState> {
	private final ModelPart waist;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final KeyframeAnimation death;
	private final KeyframeAnimation attack;
	private final KeyframeAnimation ability;

	@Override
	public void setupAnim(AMDRenderState state) {
		super.setupAnim(state);

		if (state.deathAnimationState.isStarted()) {
			this.death.apply(state.deathAnimationState, state.ageInTicks);
			return;
		}
		if (state.abilityAnimationState.isStarted()) {
			this.ability.apply(state.abilityAnimationState, state.ageInTicks);
			return;
		}
		if (state.attackAnimationState.isStarted()) {
			this.attack.apply(state.attackAnimationState, state.ageInTicks);
			return;
		}

		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		float limbSwingAmplitude = state.walkAnimationSpeed;
		float limbSwingAnimationProgress = state.walkAnimationPos;
		this.left_arm.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
		this.right_arm.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
		this.right_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
		this.left_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
	}

	public AMDModel(ModelPart root) {
		super(root);
		this.waist = root.getChild("waist");
		this.left_leg = this.waist.getChild("left_leg");
		this.right_leg = this.waist.getChild("right_leg");
		this.body = this.waist.getChild("body");
		this.head = this.body.getChild("head");
		this.left_arm = this.body.getChild("left_arm");
		this.right_arm = this.body.getChild("right_arm");
		this.attack = AMDAnimation.ATTACK.bake(root);
		this.death = AMDAnimation.DEATH.bake(root);
		this.ability = AMDAnimation.ABILITY.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition waist = partdefinition.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(46, 76).addBox(-12.0F, -8.0F, -8.0F, 24.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition left_tasset = waist.addOrReplaceChild("left_tasset", CubeListBuilder.create(), PartPose.offset(12.0F, -8.0F, 0.0F));

		left_tasset.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 78).addBox(-1.8458F, 0.0203F, -9.0F, 2.0F, 22.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition right_tasset = waist.addOrReplaceChild("right_tasset", CubeListBuilder.create(), PartPose.offset(-12.0F, -8.0F, 0.0F));

		right_tasset.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 78).mirror().addBox(-0.1542F, 0.0203F, -9.0F, 2.0F, 22.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition right_back_tasset = waist.addOrReplaceChild("right_back_tasset", CubeListBuilder.create(), PartPose.offset(-7.0F, -8.0F, 10.0F));

		right_back_tasset.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 72).mirror().addBox(-5.0F, 0.0F, -2.0F, 10.0F, 22.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition right_front_tasset = waist.addOrReplaceChild("right_front_tasset", CubeListBuilder.create(), PartPose.offset(-9.0F, -8.0F, -8.0F));

		right_front_tasset.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 72).mirror().addBox(-5.0F, 0.0F, -2.0F, 10.0F, 22.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition left_front_tasset = waist.addOrReplaceChild("left_front_tasset", CubeListBuilder.create(), PartPose.offset(9.0F, -8.0F, -8.0F));

		left_front_tasset.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 72).addBox(-5.0F, 0.0F, -2.0F, 10.0F, 22.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition left_back_tasset = waist.addOrReplaceChild("left_back_tasset", CubeListBuilder.create(), PartPose.offset(7.0F, -8.0F, 10.0F));

		left_back_tasset.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 72).addBox(-5.0F, 0.0F, -2.0F, 10.0F, 22.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		waist.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(28, 72).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 8.0F, 0.0F));

		waist.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(28, 72).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 8.0F, 0.0F));

		PartDefinition body = waist.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -24.0F, -12.0F, 28.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 48).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -31.0F, -4.0F));

		head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(104, 14).mirror().addBox(-3.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, -9.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(104, 14).addBox(-1.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(14.0F, -30.0F, 0.0F));

		left_arm.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(52, 48).addBox(-1.0F, -4.0F, -5.0F, 10.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition left_forearm = left_arm.addOrReplaceChild("left_forearm", CubeListBuilder.create().texOffs(92, 48).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 10.0F, 0.0F));

		PartDefinition left_hand = left_forearm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.0F, -3.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		left_hand.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -3.0F, -0.3927F, 0.0F, 0.0F));

		left_hand.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(80, 0).addBox(-5.0F, -1.0F, -5.0F, 9.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-14.0F, -30.0F, 0.0F));

		right_arm.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(52, 48).mirror().addBox(-9.0F, -4.0F, -5.0F, 10.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition right_forearm = right_arm.addOrReplaceChild("right_forearm", CubeListBuilder.create().texOffs(92, 48).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.0F, 10.0F, 0.0F));

		PartDefinition right_hand = right_forearm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, 3.0F, -3.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 16.0F, 0.0F));

		right_hand.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-2.0F, 0.0F, -2.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, -3.0F, -0.3927F, 0.0F, 0.0F));

		right_hand.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(80, 0).mirror().addBox(-4.0F, -1.0F, -5.0F, 9.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		right_hand.addOrReplaceChild("weapon", CubeListBuilder.create().texOffs(30, 108).addBox(-2.0F, -3.0F, -16.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(70, 108).addBox(-2.0F, -3.0F, -31.0F, 4.0F, 5.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 6.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}