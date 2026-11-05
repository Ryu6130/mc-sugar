package sugar.entity;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SugardianModel extends EntityModel<SugardianRenderState> {
	private final KeyframeAnimation death;
	private final KeyframeAnimation attack;
	private final ModelPart waist;
	private final ModelPart head;
	private final ModelPart arml;
	private final ModelPart armr;
	private final ModelPart legr;
	private final ModelPart legl;

	@Override
	public void setupAnim(SugardianRenderState state) {
		super.setupAnim(state);

		if (state.attackAnimationState.isStarted()) {
			this.attack.apply(state.attackAnimationState, state.ageInTicks);
			return;
		}
		if (state.deathAnimationState.isStarted()) {
			this.death.apply(state.deathAnimationState, state.ageInTicks);
			return;
		}
	
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		float limbSwingAmplitude = state.walkAnimationSpeed;
		float limbSwingAnimationProgress = state.walkAnimationPos;
		this.arml.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
		this.armr.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
		this.legr.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
		this.legl.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
	}

	public SugardianModel(ModelPart root) {
		super(root);
		this.waist = root.getChild(PartNames.WAIST);
		this.head = this.waist.getChild(PartNames.HEAD);
		this.arml = this.waist.getChild(PartNames.LEFT_ARM);
		this.armr = this.waist.getChild(PartNames.RIGHT_ARM);
		this.legr = this.waist.getChild(PartNames.RIGHT_LEG);
		this.legl = this.waist.getChild(PartNames.LEFT_LEG);
		this.death = SugardianAnimation.DEATH.bake(root);
		this.attack = SugardianAnimation.ATTACK.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition waist = partdefinition.addOrReplaceChild(PartNames.WAIST, CubeListBuilder.create().texOffs(0, 60).addBox(-22.0F, -17.0F, -8.0F, 28.0F, 18.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 14.0F, 0.0F));

		waist.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -32.0F, -12.0F, 48.0F, 32.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -14.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		waist.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(48, 120).addBox(-8.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -22.0F, -14.0F));

		waist.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(0, 94).addBox(0.0F, -4.0F, -6.0F, 12.0F, 48.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, -45.0F, -7.0F));

		waist.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(88, 60).addBox(-12.0F, -4.0F, -6.0F, 12.0F, 48.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, -45.0F, -7.0F));

		waist.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(48, 94).addBox(-20.0F, 0.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		waist.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(112, 120).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}
}