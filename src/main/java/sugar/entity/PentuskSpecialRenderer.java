package sugar.entity;

import java.util.function.Consumer;

import org.joml.Vector3fc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.util.Unit;

public class PentuskSpecialRenderer implements NoDataSpecialModelRenderer {
    private final PentuskModel model;

    public PentuskSpecialRenderer(PentuskModel model) {
        this.model = model;
    }

    @Override
    public void submit(
        PoseStack poseStack,
        SubmitNodeCollector collector,
        int light,
        int overlay,
        boolean hasFoil,
        int outlineColor
    ) {
        poseStack.pushPose();
        poseStack.rotateDegrees(Axis.ZP, 180);
        poseStack.translate(0, -0.25, 0);

        if (hasFoil) {
            collector.submitModel(this.model, Unit.INSTANCE, poseStack, RenderTypes.entitySolidGlint(PentuskRenderer.PENTUSK_LOCATION), light, overlay, outlineColor);
        } else {
            collector.submitModel(this.model, Unit.INSTANCE, poseStack, PentuskRenderer.PENTUSK_LOCATION, light, overlay, outlineColor);
        }

        poseStack.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        this.model.root().getExtentsForGui(poseStack, output);
    }

    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {

        public static final com.mojang.serialization.MapCodec<Unbaked> MAP_CODEC =
            com.mojang.serialization.MapCodec.unit(new Unbaked());

        @Override
        public com.mojang.serialization.MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public PentuskSpecialRenderer bake(
                SpecialModelRenderer.BakingContext context
        ) {
            return new PentuskSpecialRenderer(
                new PentuskModel(
                    context.entityModelSet().bakeLayer(
                        ModEntityModelLayers.PENTUSK
                    )
                )
            );
        }
    }
}