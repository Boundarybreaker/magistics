package T145.magistics.common.config;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchCategories;
import T145.magistics.common.Magistics;
import T145.magistics.common.blocks.BlockChestHungryEnder;
import T145.magistics.common.config.external.ModHandler;
import T145.magistics.common.items.ItemResources;
import T145.magistics.common.items.armor.ItemCruelMask;
import T145.magistics.common.items.baubles.ItemAmuletDeath;
import T145.magistics.common.items.baubles.ItemAmuletLife;
import T145.magistics.common.items.baubles.ItemBeltCleansing;
import T145.magistics.common.items.baubles.ItemBeltVigor;
import T145.magistics.common.items.baubles.ItemRingSouls;
import T145.magistics.common.tiles.TileChestHungryEnder;
import cpw.mods.fml.common.registry.GameRegistry;

public class MagisticsConfig {
	public static Configuration config;

	public static boolean colored_names, low_gfx;

	public static final String itemName[] = {
		"mystic_resources", "cruel_mask", "bauble.amulet_death", "bauble.amulet_life", "bauble.belt_cleansing", "bauble.belt_vigor", "bauble.ring_souls"
	}, blockName[] = {
		"ender_hungry_chest"
	};

	public static Item items[] = {
		new ItemResources().setCreativeTab(Magistics.tabMagistics).setHasSubtypes(true).setMaxDamage(0).setUnlocalizedName(itemName[0]),
		new ItemCruelMask(ThaumcraftApi.armorMatThaumium, 2, 0).setCreativeTab(Magistics.tabMagistics).setMaxDamage(100).setMaxStackSize(1).setUnlocalizedName(itemName[1]),
		new ItemAmuletDeath().setCreativeTab(Magistics.tabMagistics).setMaxDamage(50).setUnlocalizedName(itemName[2]),
		new ItemAmuletLife().setCreativeTab(Magistics.tabMagistics).setMaxDamage(100).setUnlocalizedName(itemName[3]),
		new ItemBeltCleansing().setCreativeTab(Magistics.tabMagistics).setMaxDamage(50).setUnlocalizedName(itemName[4]),
		new ItemBeltVigor().setCreativeTab(Magistics.tabMagistics).setMaxDamage(100).setUnlocalizedName(itemName[5]),
		new ItemRingSouls().setCreativeTab(Magistics.tabMagistics).setMaxDamage(51).setUnlocalizedName(itemName[6]),
	};

	public static Block blocks[] = {
		new BlockChestHungryEnder().setBlockName(blockName[0]).setCreativeTab(Magistics.tabMagistics).setHardness(22.5F).setLightLevel(0.5F).setResistance(1000F).setStepSound(Block.soundTypePiston)
	};

	public static Class tiles[] = {
		TileChestHungryEnder.class
	};

	public static void preInit(File configFile) {
		config = new Configuration(configFile);

		try {
			config.load();
			colored_names = config.get(config.CATEGORY_GENERAL, "colored_names", false, "Toggles whether or not blocks have colored names like in Thaumcraft 2.").getBoolean(colored_names);
			low_gfx = config.get(config.CATEGORY_GENERAL, "low_gfx", false, "Toggles or lessens graphical effects created by some blocks & items; great for low-end computers.").getBoolean(low_gfx);
		} catch (Exception e) {
			Magistics.logger.log(Level.ERROR, "An error has occurred while loading configuration properties!", e);
		} finally {
			if (config.hasChanged())
				config.save();
		}
	}

	public static void init() {
		for (Item item : items)
			GameRegistry.registerItem(item, item.getUnlocalizedName());
		for (Class tileEntity : tiles)
			GameRegistry.registerTileEntity(tileEntity, tileEntity.getSimpleName());
		GameRegistry.registerBlock(blocks[0], blockName[0]);
	}

	public static void postInit() {
		ModHandler.init();

		ResearchCategories.registerCategory(Magistics.modid.toUpperCase(), new ResourceLocation("magistics", "textures/gui/tab.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));
	}
}