package sugar.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import sugar.SugarMod;

public class WindAttackRenderer extends EntityRenderer<WindAttack, RotEntityRenderState> {
    public static final Identifier BLACK_HOLE_LOCATION =
        SugarMod.id("textures/entity/wind_attack.png");
    private final WindAttackModel model;

    public WindAttackRenderer(final EntityRendererProvider.Context context) {
        super(context);
        this.model = new WindAttackModel(context.bakeLayer(ModEntityModelLayers.WIND_ATTACK));
    }
    
    public void submit(final RotEntityRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0, -1.375, 0);
        poseStack.rotateDegrees(Axis.YP, state.yRot + 180);
        submitNodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, BLACK_HOLE_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override 
    public RotEntityRenderState createRenderState() {
        return new RotEntityRenderState();
    }

    @Override
    public void extractRenderState(final WindAttack entity, final RotEntityRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yRot = entity.getYRot(partialTicks);
        state.xRot = entity.getXRot(partialTicks);
    }
}
