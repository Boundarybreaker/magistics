package T145.magistics.client.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import T145.magistics.client.lib.Render;
import T145.magistics.common.tiles.TileInfuser;
import T145.magistics.core.Magistics;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderInfuser extends TileEntitySpecialRenderer<TileInfuser> {

	@Override
	public void render(@Nonnull TileInfuser infuser, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		drawDisk(infuser, x, y + Render.W12 + 0.01D, z);

		if (infuser.isCrafting() && infuser.getWorld().rand.nextFloat() < infuser.progress) {
			double xx = infuser.getPos().getX() + 0.5F - (infuser.getWorld().rand.nextFloat() - infuser.getWorld().rand.nextFloat()) * 0.35F;
			double yy = infuser.getPos().getY() + Render.W16;
			double zz = infuser.getPos().getZ() + 0.5F - (infuser.getWorld().rand.nextFloat() - infuser.getWorld().rand.nextFloat()) * 0.35F;
			//ParticleCreator.wispFX3(xx, yy, zz, xx, yy + infuser.getWorld().rand.nextFloat(), zz, 0.1F, infuser.isDark() ? 5 : infuser.getWorld().rand.nextInt(5), false, 0);
		}
	}

	private void drawDisk(TileInfuser infuser, double x, double y, double z) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5D, y, z + 0.5D);
		GlStateManager.pushMatrix();

		if (infuser.isCrafting()) {
			GlStateManager.rotate(infuser.getCookProgressScaled(360), 0F, 1F, 0F);
		} else {
			GlStateManager.rotate(getFrontAngle(infuser.getFront()), 0F, 1F, 0F);
		}

		GlStateManager.translate(-0.45D, 0D, -0.45D);
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();

		if (infuser.isCrafting()) {
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		} else {
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}

		bindTexture(infuser.getInfuserType().getSymbolResource());
		GlStateManager.color(1F, 1F, 1F, 1F);

		Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		if (infuser.isCrafting()) {
			GlStateManager.color(1F, 0.5F, 1F, 1F);
		} else {
			GlStateManager.color(0F, 0F, 0F, 1F);
		}

		tessellator.getBuffer().pos(0D, 0D, 0.9D).tex(0D, 1D).endVertex();
		tessellator.getBuffer().pos(0.9D, 0D, 0.9D).tex(1D, 1D).endVertex();
		tessellator.getBuffer().pos(0.9D, 0D, 0D).tex(1D, 0D).endVertex();
		tessellator.getBuffer().pos(0D, 0D, 0D).tex(0D, 0D).endVertex();
		tessellator.draw();

		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

	public static float getFrontAngle(EnumFacing facing) {
		switch (facing) {
		case NORTH:
			return 180F;
		case WEST:
			return 270F;
		case EAST:
			return 90F;
		default:
			return 0F;
		}
	}
}