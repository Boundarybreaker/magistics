package T145.magistics.client.render.blocks;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import T145.magistics.client.lib.BlockRenderer;
import T145.magistics.client.lib.ClientBakery;
import T145.magistics.client.lib.RenderCubes;
import T145.magistics.tiles.crafting.TileCrucible;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCrucible extends TileEntitySpecialRenderer<TileCrucible> {

	@Override
	public void renderTileEntityAt(@Nonnull TileCrucible crucible, double x, double y, double z, float partialTicks, int destroyStage) {
		if (crucible.getQuints() > 0 && crucible.isNormal()) {
			renderLiquid(crucible, x, y, z, partialTicks);
		}
	}

	private void renderLiquid(TileCrucible crucible, double x, double y, double z, float partialTicks) {
		float amount = Math.min(crucible.getQuints(), crucible.getMaxQuints());
		float level = 0.75F * (amount / crucible.getMaxQuints());

		if (crucible.isFull() || crucible.isOverflowing()) {
			level -= 0.001D;
		}

		if (amount > 0.01F) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.disableLighting();

			Tessellator tess = Tessellator.getInstance();
			RenderCubes render = new RenderCubes();

			render.setRenderBounds(BlockRenderer.W1 + 0.001D, BlockRenderer.W4, BlockRenderer.W1 + 0.001D, 0.999D - BlockRenderer.W1, BlockRenderer.W4 + level, 0.999D - BlockRenderer.W1);
			tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			render.renderFaceYPos(0D, 0D, 0D, ClientBakery.INSTANCE.quintFluid, 1F, 1F, 1F, 210);
			tess.draw();

			GlStateManager.enableLighting();
			GlStateManager.color(1F, 1F, 1F, 1F);
			GlStateManager.popMatrix();
		}
	}
}