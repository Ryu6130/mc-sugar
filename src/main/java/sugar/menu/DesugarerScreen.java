package sugar.menu;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import sugar.SugarMod;

public class DesugarerScreen extends AbstractContainerScreen<DesugarerMenu> {
	private static final Identifier CONTAINER_TEXTURE =
        SugarMod.id("textures/gui/container/desugarer.png");
	private static final Identifier PROGRESS_SPRITE =
		Identifier.withDefaultNamespace("container/furnace/burn_progress");

	public DesugarerScreen(DesugarerMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractBackground(graphics, mouseX, mouseY, delta);
		graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
		
		int burnProgressWidth = Mth.ceil(((DesugarerMenu)this.menu).getProgress() * 24.0F);
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_SPRITE, 24, 16, 0, 0, this.leftPos + 79, this.topPos + 34, burnProgressWidth, 16);
	}
}
