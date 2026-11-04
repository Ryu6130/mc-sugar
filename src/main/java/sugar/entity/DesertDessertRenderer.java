package sugar.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import sugar.SugarMod;

public class DesertDessertRenderer extends MobRenderer<DesertDessert, DesertDessertRenderState, DesertDessertModel> {
	private static final Identifier TEXTURE = SugarMod.id("textures/entity/desert_dessert.png");

	public DesertDessertRenderer(EntityRendererProvider.Context context) {
		super(context, new DesertDessertModel(context.bakeLayer(ModEntityModelLayers.DESERT_DESSERT)), 0.625f);
	}

	@Override
	public DesertDessertRenderState createRenderState() {
		return new DesertDessertRenderState();
	}

	@Override
	public Identifier getTextureLocation(DesertDessertRenderState state) {
		return TEXTURE;
	}
}