package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class BerryGodRenderer extends MobRenderer<BerryGod, BerryGodRenderState, BerryGodModel> {
	private static final Identifier TEXTURE = SugarMod.id("textures/entity/berry_god.png");

	public BerryGodRenderer(EntityRendererProvider.Context context) {
		super(context, new BerryGodModel(context.bakeLayer(ModEntityModelLayers.BERRY_GOD)), 1.5f);
	}

    @Override
    public Identifier getTextureLocation(BerryGodRenderState state) {
        return TEXTURE;
    }

    @Override
    public BerryGodRenderState createRenderState() {
        return new BerryGodRenderState();
    }

    @Override
    protected float getFlipDegrees() {
        return 0;
    }

    @Override
    public void extractRenderState(BerryGod entity, BerryGodRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.shotAnimationState.copyFrom(entity.shotAnimationState);
        state.deathAnimationState.copyFrom(entity.deathAnimationState);
    }
}
