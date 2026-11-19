package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;

public class ArcRenderer extends HumanoidMobRenderer<Arc, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    private static final Identifier ARC_LOCATION =
        SugarMod.id("textures/entity/arc/arc.png");
    private static final Identifier BABY_ARC_LOCATION =
        SugarMod.id("textures/entity/arc/arc_baby.png");

    public ArcRenderer(Context context) {
        super(
            context,
            new HumanoidModel<>(context.bakeLayer(ModEntityModelLayers.ARC)),
            new HumanoidModel<>(context.bakeLayer(ModEntityModelLayers.BABY_ARC)),
            0.5f
        );
    }
    
    public Identifier getTextureLocation(final HumanoidRenderState state) {
        return state.isBaby ? BABY_ARC_LOCATION : ARC_LOCATION;
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }
}
