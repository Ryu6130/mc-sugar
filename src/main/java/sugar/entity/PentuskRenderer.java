package sugar.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.phys.AABB;
import sugar.SugarMod;

public class PentuskRenderer extends EntityRenderer<PentuskEntity, PentuskRenderState> {
    public static final Identifier PENTUSK_LOCATION =
        SugarMod.id("textures/entity/pentusk.png");
    private final PentuskModel model;

    public PentuskRenderer(final EntityRendererProvider.Context context) {
        super(context);
        this.model = new PentuskModel(context.bakeLayer(ModEntityModelLayers.PENTUSK));
    }
    
    public void submit(final PentuskRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.rotateDegrees(Axis.YP, state.yRot - 90.0F);
        poseStack.rotateDegrees(Axis.ZP, state.xRot + 90.0F);
        poseStack.translate(0, -0.25, 0);
        if (state.isFoil) {
            submitNodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, RenderTypes.entitySolidGlint(PENTUSK_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        } else {
            submitNodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, PENTUSK_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }

        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    protected AABB getBoundingBoxForCulling(final PentuskEntity entity, final float partialTicks) {
        return super.getBoundingBoxForCulling(entity, partialTicks).inflate((double)1.5F);
    }

    public PentuskRenderState createRenderState() {
        return new PentuskRenderState();
    }

    public void extractRenderState(final PentuskEntity entity, final PentuskRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yRot = entity.getYRot(partialTicks);
        state.xRot = entity.getXRot(partialTicks);
        state.isFoil = entity.isFoil();
    }
}
