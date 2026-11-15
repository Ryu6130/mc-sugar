package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class AlligantNestRenderer extends MobRenderer<AlligantNest, LivingEntityRenderState, AlligantNestModel>{
    private static final Identifier TEXTURE =
        SugarMod.id("textures/entity/alligant_nest.png");

    public AlligantNestRenderer(EntityRendererProvider.Context context) {
		super(context, new AlligantNestModel(context.bakeLayer(ModEntityModelLayers.ALLIGANT_NEST)), 0.75f);
	}

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }
    
    @Override
    protected float getFlipDegrees() {
        return 0;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public RenderType getRenderType(
        LivingEntityRenderState state,
        boolean bodyVisible,
        boolean translucent,
        boolean glowing
    ) {
        return RenderTypes.entityTranslucent(TEXTURE);
    }
}
