package sugar.entity;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import sugar.SugarMod;

public class BerryGodModel extends EntityModel<BerryGodRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION =
		new ModelLayerLocation(SugarMod.id("berry_god"), "main");
	private final KeyframeAnimation death;
	private final KeyframeAnimation attack;
	private final KeyframeAnimation shot;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart shoulder;
	private final ModelPart armr;
	private final ModelPart arml;

	@Override
	public void setupAnim(BerryGodRenderState state) {
		super.setupAnim(state);

		if (state.deathAnimationState.isStarted()) {
			this.death.apply(state.deathAnimationState, state.ageInTicks);
			return;
		}
		if (state.shotAnimationState.isStarted()) {
			this.shot.apply(state.shotAnimationState, state.ageInTicks);
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
		this.arml.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
		this.armr.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
	}

	public BerryGodModel(ModelPart root) {
		super(root);
		this.body = root.getChild(PartNames.BODY);
		this.head = this.body.getChild(PartNames.HEAD);
		this.shoulder = this.body.getChild("shoulder");
		this.armr = this.shoulder.getChild(PartNames.RIGHT_ARM);
		this.arml = this.shoulder.getChild(PartNames.LEFT_ARM);
		this.attack = BerryGodAnimation.ATTACK.bake(root);
		this.death = BerryGodAnimation.DEATH.bake(root);
		this.shot = BerryGodAnimation.SHOT.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(40, 52).addBox(-1.0F, 7.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 52).addBox(-3.0F, 5.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(48, 19).addBox(-5.0F, 3.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(48, 0).addBox(-7.0F, 1.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 28).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition shoulder = body.addOrReplaceChild("shoulder", CubeListBuilder.create().texOffs(48, 15).addBox(-12.0F, -1.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition armr = shoulder.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(-12.0F, 0.0F, 0.0F));

		armr.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 52).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		armr.addOrReplaceChild("handr", CubeListBuilder.create().texOffs(48, 30).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 15.0F, 0.0F));

		PartDefinition arml = shoulder.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(12.0F, 0.0F, 0.0F));

		arml.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 52).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		arml.addOrReplaceChild("handl", CubeListBuilder.create().texOffs(48, 46).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 15.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}