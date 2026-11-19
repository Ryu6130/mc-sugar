package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class GnomeRenderer extends MobRenderer<Gnome, LivingEntityRenderState, GnomeModel> {
    private static final Identifier TEXTURE =
        SugarMod.id("textures/entity/gnome.png");

    public GnomeRenderer(Context context) {
        super(context, new GnomeModel(context.bakeLayer(ModEntityModelLayers.GNOME)), 0.5f);
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
