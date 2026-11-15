package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class AlligantRenderer extends MobRenderer<Alligant, LivingEntityRenderState, AlligantModel> {
    private static final Identifier TEXTURE =
        SugarMod.id("textures/entity/alligant.png");

    public AlligantRenderer(EntityRendererProvider.Context context) {
		super(context, new AlligantModel(context.bakeLayer(ModEntityModelLayers.ALLIGANT)), 1f);
	}

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
