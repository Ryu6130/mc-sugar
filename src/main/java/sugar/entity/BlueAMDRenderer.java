package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class BlueAMDRenderer extends MobRenderer<BlueAMD, AMDRenderState, AMDModel> {
    public BlueAMDRenderer(Context context) {
        super(context, new AMDModel(context.bakeLayer(ModEntityModelLayers.AMD)), 1.25f);
    }

    private static final Identifier TEXTURE = SugarMod.id("textures/entity/blue_amd.png");

    @Override
    public AMDRenderState createRenderState() {
        return new AMDRenderState();
    }

    @Override
    protected float getFlipDegrees() {
        return 0;
    }

    @Override
    public Identifier getTextureLocation(AMDRenderState state) {
        return TEXTURE;
    }

    @Override
    public void extractRenderState(BlueAMD entity, AMDRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.abilityAnimationState.copyFrom(entity.abilityAnimationState);
        state.deathAnimationState.copyFrom(entity.deathAnimationState);
    }
}
