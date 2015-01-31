package T145.magistics.client.renderers.block;

import net.minecraft.client.model.ModelChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import T145.magistics.client.lib.TextureHelper;

import com.dynious.refinedrelocation.lib.Resources;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.ironchest.IronChestType;

@SideOnly(Side.CLIENT)
public class BlockSortingChestHungryMetalItemRenderer implements IItemRenderer {
	private ModelChest model = new ModelChest();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		render(item, 0);
		render(item, 1);
	}

	public void render(ItemStack itemStack, int renderPass) {
		if (itemStack == null) {
			return;
		}

		if (renderPass == 0) {
			IronChestType type = IronChestType.values()[itemStack.getItemDamage()];
			ResourceLocation loc = TextureHelper.ironChestTextures[type.ordinal()];
			if (loc != null) {
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(loc);
			}
		} else {
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(Resources.MODEL_TEXTURE_OVERLAY_CHEST);
		}
		GL11.glPushMatrix();
		GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, 1.0F, 1.0F);
		GL11.glScalef(1.0F, -1F, -1F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		model.renderAll();
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}