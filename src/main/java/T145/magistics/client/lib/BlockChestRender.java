package T145.magistics.client.lib;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockChestRender implements ISimpleBlockRenderingHandler {
	private int id = 0;
	private ResourceLocation[] textures;
	private ResourceLocation overlay;
	private TileEntity chest;

	public BlockChestRender(int renderID, ResourceLocation[] resources) {
		id = renderID;
		textures = resources;
	}

	public BlockChestRender(int renderID, ResourceLocation[] resources, ResourceLocation overlay) {
		this(renderID, resources);
		this.overlay = overlay;
	}

	public BlockChestRender(int renderID, TileEntity tile) {
		id = renderID;
		chest = tile;
	}

	public boolean hasOverlay() {
		return overlay != null;
	}

	public boolean hasTileEntity() {
		return chest != null;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
		if (hasTileEntity()) {
			InventoryChestRenderHelper.renderChest(chest);
		} else {
			Minecraft.getMinecraft().getTextureManager().bindTexture(textures[meta]);
			InventoryChestRenderHelper.renderChest(false);

			if (hasOverlay()) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(overlay);
				InventoryChestRenderHelper.renderChest(true);
			}
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}
}