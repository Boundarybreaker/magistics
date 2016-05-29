package T145.magistics.plugins;

import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import T145.magistics.blocks.BlockArcaneLampRedstone;
import T145.magistics.blocks.BlockChestHungryEnder;
import T145.magistics.blocks.BlockChestHungryTrapped;
import T145.magistics.blocks.BlockChthonianFurnace;
import T145.magistics.blocks.BlockEntropicDispenser;
import T145.magistics.blocks.BlockEverfullUrn;
import T145.magistics.blocks.BlockInfuser;
import T145.magistics.blocks.BlockInfuserItem;
import T145.magistics.blocks.BlockNetherFurnace;
import T145.magistics.blocks.BlockVoidstone;
import T145.magistics.client.render.blocks.RenderBlockChest;
import T145.magistics.client.render.blocks.RenderBlockChthonianFurnace;
import T145.magistics.client.render.blocks.RenderBlockEverfullUrn;
import T145.magistics.client.render.blocks.RenderBlockInfuser;
import T145.magistics.client.render.blocks.RenderBlockLootCrate;
import T145.magistics.client.render.blocks.RenderBlockLootUrn;
import T145.magistics.client.render.tiles.RenderChestHungryEnder;
import T145.magistics.client.render.tiles.RenderChestHungryTrapped;
import T145.magistics.client.render.tiles.RenderChthonianFurnace;
import T145.magistics.client.render.tiles.RenderInfuser;
import T145.magistics.items.ItemDummy;
import T145.magistics.items.ItemShardFragment;
import T145.magistics.plugins.core.Plugin;
import T145.magistics.research.ResearchHandler;
import T145.magistics.tiles.TileArcaneLampRedstone;
import T145.magistics.tiles.TileChestHungryEnder;
import T145.magistics.tiles.TileChestHungryTrapped;
import T145.magistics.tiles.TileChthonianFurnace;
import T145.magistics.tiles.TileEverfullUrn;
import T145.magistics.tiles.TileInfuser;
import T145.magistics.tiles.TileInfuserDark;
import T145.magistics.tiles.TileNetherFurnace;
import T145.magistics.utils.RegistrationUtil;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class PluginThaumcraft extends Plugin {
	public PluginThaumcraft() {
		super("Thaumcraft", true);
	}

	@Override
	public void preInit() {
		/*ConfigBlocks.blockFluidDeath.setCreativeTab(null);
		ConfigBlocks.blockFluidPure.setCreativeTab(null);
		ConfigBlocks.blockFluxGas.setCreativeTab(null);
		ConfigBlocks.blockFluxGoo.setCreativeTab(null);
		ConfigBlocks.blockTaint.setCreativeTab(null);
		ConfigBlocks.blockTaintFibres.setCreativeTab(null);*/
	}

	@Override
	public void init() {
		GameRegistry.registerTileEntity(TileNetherFurnace.class, TileNetherFurnace.class.getSimpleName());
		GameRegistry.registerBlock(BlockNetherFurnace.ACTIVE, BlockNetherFurnace.ACTIVE.getUnlocalizedName() + "_on");
		GameRegistry.registerBlock(BlockNetherFurnace.INACTIVE, BlockNetherFurnace.INACTIVE.getUnlocalizedName() + "_off");
		GameRegistry.registerTileEntity(TileChthonianFurnace.class, TileChthonianFurnace.class.getSimpleName());
		GameRegistry.registerBlock(BlockChthonianFurnace.INSTANCE, BlockChthonianFurnace.INSTANCE.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileChestHungryTrapped.class, TileChestHungryTrapped.class.getSimpleName());
		GameRegistry.registerBlock(BlockChestHungryTrapped.INSTANCE, BlockChestHungryTrapped.INSTANCE.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileChestHungryEnder.class, TileChestHungryEnder.class.getSimpleName());
		GameRegistry.registerBlock(BlockChestHungryEnder.INSTANCE, BlockChestHungryEnder.INSTANCE.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileArcaneLampRedstone.class, TileArcaneLampRedstone.class.getSimpleName());
		GameRegistry.registerBlock(BlockArcaneLampRedstone.ACTIVE, BlockArcaneLampRedstone.ACTIVE.getUnlocalizedName() + "_on");
		GameRegistry.registerBlock(BlockArcaneLampRedstone.INACTIVE, BlockArcaneLampRedstone.INACTIVE.getUnlocalizedName() + "_off");

		GameRegistry.registerTileEntity(TileEverfullUrn.class, TileEverfullUrn.class.getSimpleName());
		GameRegistry.registerBlock(BlockEverfullUrn.INSTANCE, BlockEverfullUrn.INSTANCE.getUnlocalizedName());
		GameRegistry.registerBlock(BlockVoidstone.INSTANCE, BlockVoidstone.INSTANCE.getUnlocalizedName());

		GameRegistry.registerTileEntity(TileInfuser.class, TileInfuser.class.getSimpleName());
		GameRegistry.registerTileEntity(TileInfuserDark.class, TileInfuserDark.class.getSimpleName());
		GameRegistry.registerBlock(BlockInfuser.INSTANCE, BlockInfuserItem.class, BlockInfuser.INSTANCE.getUnlocalizedName());

		GameRegistry.registerBlock(BlockEntropicDispenser.INSTANCE, BlockEntropicDispenser.INSTANCE.getUnlocalizedName());

		GameRegistry.registerItem(ItemShardFragment.INSTANCE, ItemShardFragment.INSTANCE.getUnlocalizedName());
		RegistrationUtil.registerItem(Thaumcraft.MODID, ItemDummy.INFERNAL_FURNACE);
	}

	@Override
	public void postInit() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileChestHungryTrapped.class, RenderChestHungryTrapped.INSTANCE);
		RenderingRegistry.registerBlockHandler(new RenderBlockChest(BlockChestHungryTrapped.INSTANCE.getRenderType(), new TileChestHungryTrapped()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileChestHungryEnder.class, RenderChestHungryEnder.INSTANCE);
		RenderingRegistry.registerBlockHandler(new RenderBlockChest(BlockChestHungryEnder.INSTANCE.getRenderType(), new TileChestHungryEnder()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileChthonianFurnace.class, RenderChthonianFurnace.INSTANCE);
		RenderingRegistry.registerBlockHandler(new RenderBlockChthonianFurnace());
		RenderingRegistry.registerBlockHandler(new RenderBlockEverfullUrn());

		ClientRegistry.bindTileEntitySpecialRenderer(TileInfuser.class, RenderInfuser.INSTANCE);
		RenderingRegistry.registerBlockHandler(RenderBlockInfuser.INSTANCE);

		int newBlockLootCrateRI = RenderingRegistry.getNextAvailableRenderId();
		ConfigBlocks.blockLootCrateRI = newBlockLootCrateRI;
		RenderingRegistry.registerBlockHandler(new RenderBlockLootCrate(newBlockLootCrateRI));

		int newBlockLootUrnRI = RenderingRegistry.getNextAvailableRenderId();
		ConfigBlocks.blockLootUrnRI = newBlockLootUrnRI;
		RenderingRegistry.registerBlockHandler(new RenderBlockLootUrn(newBlockLootUrnRI));

		ResearchHandler.registerResearch();
	}
}