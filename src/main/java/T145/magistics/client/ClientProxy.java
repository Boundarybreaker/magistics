package T145.magistics.client;

import T145.magistics.common.CommonProxy;
import T145.magistics.common.config.ModBlocks;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void postInit() {
		for (Class tile : ModBlocks.tiles)
			ClientRegistry.bindTileEntitySpecialRenderer(tile, ModBlocks.tileRenderers.get(tile));
		for (ISimpleBlockRenderingHandler blockRenderer : ModBlocks.blockRenderers)
			RenderingRegistry.registerBlockHandler(blockRenderer);
	}
}