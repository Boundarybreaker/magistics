package T145.magistics.common.config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import thaumcraft.common.config.Config;
import T145.magistics.client.lib.ChestRenderer;
import T145.magistics.client.lib.ItemChestRenderer;
import T145.magistics.client.renderers.BlockCrystalStorageRenderer;
import T145.magistics.client.renderers.BlockEverfullUrnRenderer;
import T145.magistics.client.renderers.TileChestHungryAlchemicalRenderer;
import T145.magistics.client.renderers.TileChestHungryEnderRenderer;
import T145.magistics.client.renderers.TileChestHungryMetalRenderer;
import T145.magistics.client.renderers.TileSortingChestHungryAlchemicalRenderer;
import T145.magistics.client.renderers.TileSortingChestHungryRenderer;
import T145.magistics.common.Magistics;
import T145.magistics.common.blocks.BlockChestHungryAlchemical;
import T145.magistics.common.blocks.BlockChestHungryEnder;
import T145.magistics.common.blocks.BlockChestHungryMetal;
import T145.magistics.common.blocks.BlockChestHungryMetalItem;
import T145.magistics.common.blocks.BlockCrystalStorageBasic;
import T145.magistics.common.blocks.BlockCrystalStorageBrick;
import T145.magistics.common.blocks.BlockCrystalStorageEngineeringDark;
import T145.magistics.common.blocks.BlockCrystalStorageEngineeringLight;
import T145.magistics.common.blocks.BlockCrystalStorageItem;
import T145.magistics.common.blocks.BlockCrystalStoragePlate;
import T145.magistics.common.blocks.BlockCrystalStoragePlatform;
import T145.magistics.common.blocks.BlockCrystalStorageShield;
import T145.magistics.common.blocks.BlockCrystalStorageStructure;
import T145.magistics.common.blocks.BlockCrystalStorageStructureItem;
import T145.magistics.common.blocks.BlockEverfullUrn;
import T145.magistics.common.blocks.BlockSortingChestHungry;
import T145.magistics.common.blocks.BlockSortingChestHungryAlchemical;
import T145.magistics.common.blocks.BlockSortingChestHungryAlchemicalItem;
import T145.magistics.common.blocks.BlockSortingChestHungryMetal;
import T145.magistics.common.blocks.BlockThaumicEnchanter;
import T145.magistics.common.lib.ModRegistry;
import T145.magistics.common.tiles.TileChestHungryAlchemical;
import T145.magistics.common.tiles.TileChestHungryEnder;
import T145.magistics.common.tiles.TileChestHungryMetal;
import T145.magistics.common.tiles.TileEverfullUrn;
import T145.magistics.common.tiles.TileSortingChestHungry;
import T145.magistics.common.tiles.TileSortingChestHungryAlchemical;
import T145.magistics.common.tiles.TileThaumicEnchanter;

import com.dynious.refinedrelocation.lib.Resources;
import com.pahimar.ee3.item.ItemBlockAlchemicalChest;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	private static Configuration config;
	public static final String CATEGORY_BLOCKS = "Blocks", CATEGORY_ITEMS = "Items";

	public static boolean debug = false, blocks[], items[];

	public static void sync() {
		debug = config.getBoolean("Debug", config.CATEGORY_GENERAL, false, "Toggles advanced log output.");
	}

	public static void onConfigChanged(String modid, OnConfigChangedEvent e) {
		if (e.modID.equals(modid)) {
			sync();

			if (config != null && config.hasChanged())
				config.save();
		}
	}

	public static void loadMetadata(ModMetadata meta, String modid, String version) {
		meta.autogenerated = false;
		meta.modId = meta.name = modid;
		meta.version = version;
		meta.description = "Adding some logistics to Thaumcraft!";
		meta.url = "https://github.com/T145/magistics";
		meta.updateUrl = "https://github.com/T145/magistics/releases";
		meta.authorList.add("T145");
		meta.credits = "Texture team is awesome, and so are the fans!";
		meta.logoFile = "logo.png";
	}

	public static void preInit(FMLPreInitializationEvent e, Logger logger) {
		try {
			config = new Configuration(e.getSuggestedConfigurationFile());
			config.copyCategoryProps(Config.config, new String[] { "enchantments", "monster_spawning", "world_generation", "world_regeneration" });
			config.addCustomCategoryComment(CATEGORY_BLOCKS, "Blocks added by Magistics");
			config.addCustomCategoryComment(CATEGORY_ITEMS, "Items added by Magistics");
			config.load();
			sync();
			config.save();
		} catch (Exception ex) {
			logger.log(Level.ERROR, "A fatal error has occurred while reading configuration properties!");
		} finally {
			if (config != null)
				config.save();
		}
	}

	public static Block
	blockCrystalStorage = new BlockCrystalStorageBasic(),
	blockCrystalStorageBrick = new BlockCrystalStorageBrick(),
	blockCrystalStoragePlate = new BlockCrystalStoragePlate(),
	blockCrystalStoragePlatform = new BlockCrystalStoragePlatform(),
	blockCrystalStorageShield = new BlockCrystalStorageShield(),
	blockCrystalStorageStructure = new BlockCrystalStorageStructure(),
	blockCrystalStorageBrickEngineeringLight = new BlockCrystalStorageEngineeringLight(),
	blockCrystalStorageBrickEngineeringDark = new BlockCrystalStorageEngineeringDark(),

	blockThaumicEnchanter = new BlockThaumicEnchanter(),
	blockEverfullUrn = new BlockEverfullUrn(),

	blockChestHungryEnder = new BlockChestHungryEnder().setBlockName("hungry_ender_chest"),
	blockChestHungryAlchemical = new BlockChestHungryAlchemical().setBlockName("hungry_achemical_chest"),
	blockSortingChestHungry = new BlockSortingChestHungry().setBlockName("sorting_hungry_chest"),
	blockSortingChestHungryAlchemical = new BlockSortingChestHungryAlchemical().setBlockName("sorting_hungry_alchemical_chest"),
	blockSortingChestHungryMetal = new BlockSortingChestHungryMetal().setBlockName("sorting_hungry_metal_chest");

	public static BlockChestHungryMetal blockChestHungryMetal = new BlockChestHungryMetal();

	public static void init() {
		ModRegistry reg = Magistics.getRegistry();

		reg.addBlock(blockCrystalStorage, BlockCrystalStorageItem.class);
		reg.addBlock(blockCrystalStorageBrick, BlockCrystalStorageItem.class);
		reg.addBlock(blockCrystalStoragePlate, BlockCrystalStorageItem.class);
		reg.addBlock(blockCrystalStoragePlatform, BlockCrystalStorageItem.class);
		reg.addBlock(blockCrystalStorageShield, BlockCrystalStorageItem.class);
		reg.addBlock(blockCrystalStorageStructure, BlockCrystalStorageStructureItem.class);
		reg.addBlock(blockCrystalStorageBrickEngineeringLight, BlockCrystalStorageItem.class);
		reg.addBlock(blockCrystalStorageBrickEngineeringDark, BlockCrystalStorageItem.class);

		reg.addBlockRenderer(new BlockCrystalStorageRenderer(blockCrystalStorage.getRenderType()));
		reg.addBlockRenderer(new BlockCrystalStorageRenderer(blockCrystalStorageBrick.getRenderType()));
		reg.addBlockRenderer(new BlockCrystalStorageRenderer(blockCrystalStoragePlate.getRenderType()));
		reg.addBlockRenderer(new BlockCrystalStorageRenderer(blockCrystalStoragePlatform.getRenderType()));
		reg.addBlockRenderer(new BlockCrystalStorageRenderer(blockCrystalStorageShield.getRenderType()));
		reg.addBlockRenderer(new BlockCrystalStorageRenderer(blockCrystalStorageStructure.getRenderType()));
		reg.addBlockRenderer(new BlockCrystalStorageRenderer(blockCrystalStorageBrickEngineeringLight.getRenderType()));
		reg.addBlockRenderer(new BlockCrystalStorageRenderer(blockCrystalStorageBrickEngineeringDark.getRenderType()));

		reg.addBlock(blockChestHungryEnder);
		reg.addTile(TileChestHungryEnder.class);

		reg.addBlockRenderer(new ChestRenderer(blockChestHungryEnder.getRenderType(), new TileChestHungryEnder()));
		reg.addTileRenderer(TileChestHungryEnder.class, new TileChestHungryEnderRenderer());

		if (Loader.isModLoaded("EE3")) {
			if (debug)
				Magistics.logger.info("EE3 detected; compatibility loaded.");

			reg.addBlock(blockChestHungryAlchemical, ItemBlockAlchemicalChest.class);
			reg.addTile(TileChestHungryAlchemical.class);

			reg.addBlockRenderer(new ChestRenderer(blockChestHungryAlchemical.getRenderType(), new ResourceLocation[] {
				new ResourceLocation("magistics", "textures/models/chest_hungry/alchemical_small.png"),
				new ResourceLocation("magistics", "textures/models/chest_hungry/alchemical_medium.png"),
				new ResourceLocation("magistics", "textures/models/chest_hungry/alchemical_large.png")
			}));
			reg.addTileRenderer(TileChestHungryAlchemical.class, new TileChestHungryAlchemicalRenderer());
		}

		if (Loader.isModLoaded("IronChest")) {
			if (debug)
				Magistics.logger.info("IronChest detected; compatibility loaded.");

			reg.addBlock(blockChestHungryMetal, BlockChestHungryMetalItem.class);
			reg.addTile(TileChestHungryMetal.class);

			reg.addBlockRenderer(new ChestRenderer(blockChestHungryMetal.getRenderType(), TileChestHungryMetalRenderer.getChestTextures()));
			reg.addTileRenderer(TileChestHungryMetal.class, new TileChestHungryMetalRenderer());
		}

		if (Loader.isModLoaded("RefinedRelocation")) {
			if (debug)
				Magistics.logger.info("Refined Relocation detected; compatibility loaded.");

			reg.addBlock(blockSortingChestHungry);
			reg.addTile(TileSortingChestHungry.class);

			reg.addItemRenderer(Item.getItemFromBlock(blockSortingChestHungry), new ItemChestRenderer(new ResourceLocation[] {
					new ResourceLocation("thaumcraft", "textures/models/chesthungry.png")
			}, Resources.MODEL_TEXTURE_OVERLAY_CHEST));
			reg.addTileRenderer(TileSortingChestHungry.class, new TileSortingChestHungryRenderer());

			reg.addBlock(blockSortingChestHungryAlchemical, BlockSortingChestHungryAlchemicalItem.class);
			reg.addTile(TileSortingChestHungryAlchemical.class);

			reg.addItemRenderer(Item.getItemFromBlock(blockSortingChestHungryAlchemical), new ItemChestRenderer(new ResourceLocation[] {
					new ResourceLocation("magistics", "textures/models/chest_hungry/alchemical_small.png"),
					new ResourceLocation("magistics", "textures/models/chest_hungry/alchemical_medium.png"),
					new ResourceLocation("magistics", "textures/models/chest_hungry/alchemical_large.png")
			}, Resources.MODEL_TEXTURE_OVERLAY_ALCHEMICAL_CHEST));
			reg.addTileRenderer(TileSortingChestHungryAlchemical.class, new TileSortingChestHungryAlchemicalRenderer());
		}

		reg.addBlock(blockThaumicEnchanter);
		reg.addTile(TileThaumicEnchanter.class);

		reg.addBlock(blockEverfullUrn);
		reg.addTile(TileEverfullUrn.class);

		reg.addBlockRenderer(new BlockEverfullUrnRenderer());
	}

	public static void postInit() {
	}
}