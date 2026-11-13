package sugar.entity;

import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class SweeperRenderer extends CreeperRenderer {
	private static final Identifier TEXTURE =
        SugarMod.id("textures/entity/sweeper.png");

    public SweeperRenderer(Context context) {
        super(context);
    }
    
    public Identifier getTextureLocation(final CreeperRenderState state) {
        return TEXTURE;
    }
}
