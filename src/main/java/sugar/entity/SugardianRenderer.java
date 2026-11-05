package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class SugardianRenderer extends MobRenderer<Sugardian, SugardianRenderState, SugardianModel> {
	private static final Identifier TEXTURE = SugarMod.id("textures/entity/sugardian.png");

	public SugardianRenderer(EntityRendererProvider.Context context) {
		super(context, new SugardianModel(context.bakeLayer(ModEntityModelLayers.SUGARDIAN)), 2f);
	}

    @Override
    public Identifier getTextureLocation(SugardianRenderState state) {
        return TEXTURE;
    }

    @Override
    public SugardianRenderState createRenderState() {
        return new SugardianRenderState();
    }

    @Override
    protected float getFlipDegrees() {
        return 0;
    }

    @Override
    public void extractRenderState(Sugardian entity, SugardianRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.deathAnimationState.copyFrom(entity.deathAnimationState);
    }
}
