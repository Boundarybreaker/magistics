package T145.magistics.common.config;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import thaumcraft.common.config.Config;
import T145.magistics.client.lib.ChestRenderer;
import T145.magistics.client.renderers.BlockCrystalStorageRenderer;
import T145.magistics.client.renderers.TileChestHungryEnderRenderer;
import T145.magistics.client.renderers.TileChestHungryMetalRenderer;
import T145.magistics.common.Magistics;
import T145.magistics.common.blocks.BlockChestHungryEnder;
import T145.magistics.common.blocks.BlockChestHungryMetal;
import T145.magistics.common.blocks.BlockChestHungryMetalItem;
import T145.magistics.common.blocks.BlockCrystalStorageBasic;
import T145.magistics.common.blocks.BlockCrystalStorageBrick;
import T145.magistics.common.blocks.BlockCrystalStorageBrickEngineeringDark;
import T145.magistics.common.blocks.BlockCrystalStorageBrickEngineeringLight;
import T145.magistics.common.blocks.BlockCrystalStorageItem;
import T145.magistics.common.blocks.BlockCrystalStoragePlate;
import T145.magistics.common.blocks.BlockCrystalStoragePlatform;
import T145.magistics.common.blocks.BlockCrystalStorageShield;
import T145.magistics.common.blocks.BlockCrystalStorageStructure;
import T145.magistics.common.blocks.BlockCrystalStorageStructureItem;
import T145.magistics.common.lib.ModObjects;
import T145.magistics.common.tiles.TileChestHungryEnder;
import T145.magistics.common.tiles.TileChestHungryMetal;
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

	public static Configuration getConfig() {
		return config;
	}

	public static Block
	blockCrystalStorage = new BlockCrystalStorageBasic(),
	blockCrystalStorageBrick = new BlockCrystalStorageBrick(),
	blockCrystalStoragePlate = new BlockCrystalStoragePlate(),
	blockCrystalStoragePlatform = new BlockCrystalStoragePlatform(),
	blockCrystalStorageShield = new BlockCrystalStorageShield(),
	blockCrystalStorageStructure = new BlockCrystalStorageStructure(),
	blockCrystalStorageBrickEngineeringLight = new BlockCrystalStorageBrickEngineeringLight(),
	blockCrystalStorageBrickEngineeringDark = new BlockCrystalStorageBrickEngineeringDark(),

	blockChestHungryEnder = new BlockChestHungryEnder().setBlockName("hungry_ender_chest");

	public static BlockChestHungryMetal blockChestHungryMetal = new BlockChestHungryMetal();

	public static void init() {
		ModObjects reg = ModObjects.getRegistry();
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

		if (Loader.isModLoaded("IronChest")) {
			if (debug)
				Magistics.logger.info("IronChest detected; compatibility loaded.");

			reg.addBlock(blockChestHungryMetal, BlockChestHungryMetalItem.class);
			reg.addTile(TileChestHungryMetal.class);

			reg.addBlockRenderer(new ChestRenderer(blockChestHungryMetal.getRenderType(), TileChestHungryMetalRenderer.getChestTextures()));
			reg.addTileRenderer(TileChestHungryMetal.class, new TileChestHungryMetalRenderer());
		}

		reg.registerObjects();
		reg.registerRenderers();
	}

	public static void postInit() {
	}
}