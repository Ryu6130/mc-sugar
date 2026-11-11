package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class HermitDragonRenderer extends MobRenderer<HermitDragon, HermitDragonRenderState, HermitDragonModel> {
    private static final Identifier TEXTURE =
        SugarMod.id("textures/entity/hermit_dragon.png");

	public HermitDragonRenderer(EntityRendererProvider.Context context) {
		super(context, new HermitDragonModel(context.bakeLayer(ModEntityModelLayers.HERMIT_DRAGON)), 2f);
	}

    @Override
    public Identifier getTextureLocation(HermitDragonRenderState state) {
        return TEXTURE;
    }

    @Override
    public HermitDragonRenderState createRenderState() {
        return new HermitDragonRenderState();
    }

    @Override
    public void extractRenderState(HermitDragon entity, HermitDragonRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.jawRot = entity.getJawRot();
    }
}
