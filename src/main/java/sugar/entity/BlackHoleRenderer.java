package sugar.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import sugar.SugarMod;

public class BlackHoleRenderer extends EntityRenderer<BlackHole, EntityRenderState> {
    public static final Identifier BLACK_HOLE_LOCATION =
        SugarMod.id("textures/entity/black_hole.png");
    private final BlackHoleModel model;

    public BlackHoleRenderer(final EntityRendererProvider.Context context) {
        super(context);
        this.model = new BlackHoleModel(context.bakeLayer(ModEntityModelLayers.BLACK_HOLE));
    }
    
    public void submit(final EntityRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0, -1, 0);
        submitNodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, BLACK_HOLE_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override 
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
