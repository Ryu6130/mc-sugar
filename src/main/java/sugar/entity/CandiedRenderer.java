package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;
import net.minecraft.client.renderer.entity.ZombieRenderer;

public class CandiedRenderer extends ZombieRenderer {
    private static final Identifier CANDIED_LOCATION =
        SugarMod.id("textures/entity/candied/candied.png");
    private static final Identifier BABY_CANDIED_LOCATION =
        SugarMod.id("textures/entity/candied/candied_baby.png");

    public CandiedRenderer(Context context) {
        super(context);
    }
    
    public Identifier getTextureLocation(final ZombieRenderState state) {
        return state.isBaby ? BABY_CANDIED_LOCATION : CANDIED_LOCATION;
    }
}
