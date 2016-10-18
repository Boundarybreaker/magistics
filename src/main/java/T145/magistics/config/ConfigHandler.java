package T145.magistics.config;

import T145.magistics.Magistics;
import T145.magistics.api.IFMLEventHandler;
import T145.magistics.api.MagisticsApi;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler implements IFMLEventHandler {

	private Configuration config;

	public Configuration get() {
		return config;
	}

	private boolean addProperty(String category, String key, boolean value, String comment) {
		Property property = config.get(category, key, value);
		property.setComment(comment);
		return property.getBoolean();
	}

	private void sync() {
	}

	private void save() {
		if (config.getConfigFile().isFile() && config.hasChanged()) {
			config.save();
		}
	}

	public void update() {
		sync();
		save();
	}

	@SubscribeEvent
	public void update(OnConfigChangedEvent event) {
		if (event.getModID().equals(Magistics.MODID)) {
			update();
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		try {
			config = new Configuration(event.getSuggestedConfigurationFile());
			config.load();
			update();
		} catch (Throwable err) {
			Magistics.logger.catching(err);
		} finally {
			save();
		}
	}

	@Override
	public void init(FMLInitializationEvent event) {
		MagisticsApi.addCrucibleRecipe(Items.STICK, 0.25F);
		MagisticsApi.addCrucibleRecipe(Items.CLAY_BALL, 1F);
		MagisticsApi.addCrucibleRecipe(Items.BRICK, 2F);
		MagisticsApi.addCrucibleRecipe(Items.FLINT, 1F);
		MagisticsApi.addCrucibleRecipe(Items.COAL, 2F);
		MagisticsApi.addCrucibleRecipe(Items.SNOWBALL, 0.25F);
		MagisticsApi.addCrucibleRecipe(Items.DYE, 0, 4F);
		MagisticsApi.addCrucibleRecipe(Items.DYE, 2, 4F);
		MagisticsApi.addCrucibleRecipe(Items.DYE, 3, 25F);
		MagisticsApi.addCrucibleRecipe(Items.DYE, 4, 9F);
		MagisticsApi.addCrucibleRecipe(Items.IRON_INGOT, 5F);
		MagisticsApi.addCrucibleRecipe(Items.BEETROOT_SEEDS, 4F);
		MagisticsApi.addCrucibleRecipe(Items.MELON_SEEDS, 4F);
		MagisticsApi.addCrucibleRecipe(Items.PUMPKIN_SEEDS, 4F);
		MagisticsApi.addCrucibleRecipe(Items.WHEAT_SEEDS, 4F);
		MagisticsApi.addCrucibleRecipe(Items.FEATHER, 4F);
		MagisticsApi.addCrucibleRecipe(Items.BONE, 4F);
		MagisticsApi.addCrucibleRecipe(Items.MELON, 2F);
		MagisticsApi.addCrucibleRecipe(Items.COOKED_BEEF, 5F);
		MagisticsApi.addCrucibleRecipe(Items.BEEF, 4F);
		MagisticsApi.addCrucibleRecipe(Items.COOKED_CHICKEN, 5F);
		MagisticsApi.addCrucibleRecipe(Items.CHICKEN, 4F);
		MagisticsApi.addCrucibleRecipe(Items.COOKED_PORKCHOP, 5F);
		MagisticsApi.addCrucibleRecipe(Items.PORKCHOP, 4F);
		MagisticsApi.addCrucibleRecipe(Items.COOKED_FISH, 5F);
		MagisticsApi.addCrucibleRecipe(Items.FISH, 4F);
		MagisticsApi.addCrucibleRecipe(Items.NETHER_WART, 4F);
		MagisticsApi.addCrucibleRecipe(Items.ROTTEN_FLESH, 4F);
		MagisticsApi.addCrucibleRecipe(Items.STRING, 4F);
		MagisticsApi.addCrucibleRecipe(Items.LEATHER, 4F);
		MagisticsApi.addCrucibleRecipe(Items.WHEAT, 4F);
		MagisticsApi.addCrucibleRecipe(Items.REEDS, 4F);
		MagisticsApi.addCrucibleRecipe(Items.GUNPOWDER, 10F);
		MagisticsApi.addCrucibleRecipe(Items.GLOWSTONE_DUST, 9F);
		MagisticsApi.addCrucibleRecipe(Items.REDSTONE, 6F);
		MagisticsApi.addCrucibleRecipe(Items.MILK_BUCKET, 18F);
		MagisticsApi.addCrucibleRecipe(Items.WATER_BUCKET, 16F);
		MagisticsApi.addCrucibleRecipe(Items.LAVA_BUCKET, 17F);
		MagisticsApi.addCrucibleRecipe(Items.EGG, 5F);
		MagisticsApi.addCrucibleRecipe(Items.GOLD_INGOT, 27F);
		MagisticsApi.addCrucibleRecipe(Items.GOLD_NUGGET, 3F);
		MagisticsApi.addCrucibleRecipe(Items.SLIME_BALL, 25F);
		MagisticsApi.addCrucibleRecipe(Items.APPLE, 4F);
		MagisticsApi.addCrucibleRecipe(Items.DIAMOND, 64F);
		MagisticsApi.addCrucibleRecipe(Items.ENDER_PEARL, 64F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_13, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_CAT, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_11, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_CHIRP, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_FAR, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_MALL, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_MELLOHI, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_STAL, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_STRAD, 100F);
		MagisticsApi.addCrucibleRecipe(Items.RECORD_WARD, 100F);
		MagisticsApi.addCrucibleRecipe(Items.BLAZE_ROD, 36F);
		MagisticsApi.addCrucibleRecipe(Items.GHAST_TEAR, 64F);
		MagisticsApi.addCrucibleRecipe(Items.SPIDER_EYE, 9F);
		MagisticsApi.addCrucibleRecipe(Items.SADDLE, 64F);

		MagisticsApi.addCrucibleRecipe(Blocks.COBBLESTONE, 0.1F);
		MagisticsApi.addCrucibleRecipe(Blocks.PLANKS, 0.5F);
		MagisticsApi.addCrucibleRecipe(Blocks.MOSSY_COBBLESTONE, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.SAND, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.SANDSTONE, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.RED_SANDSTONE, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.DIRT, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.GRASS, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.GLASS, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.ICE, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.GRAVEL, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.STONE, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.WATERLILY, 3F);
		MagisticsApi.addCrucibleRecipe(Blocks.WEB, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.NETHER_BRICK, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.STONEBRICK, 1, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.STONEBRICK, 2, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.NETHERRACK, 1F);
		MagisticsApi.addCrucibleRecipe(Blocks.SOUL_SAND, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.COAL_ORE, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.WOOL, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.LOG, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.LEAVES, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.TALLGRASS, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.DEADBUSH, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.CACTUS, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.SAPLING, 2F);
		MagisticsApi.addCrucibleRecipe(Blocks.MYCELIUM, 3F);
		MagisticsApi.addCrucibleRecipe(Blocks.IRON_ORE, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.YELLOW_FLOWER, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.RED_FLOWER, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.BROWN_MUSHROOM_BLOCK, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.RED_MUSHROOM_BLOCK, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.VINE, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.PUMPKIN, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.REEDS, 4F);
		MagisticsApi.addCrucibleRecipe(Blocks.REDSTONE_ORE, 16F);
		MagisticsApi.addCrucibleRecipe(Blocks.LAPIS_ORE, 9F);
		MagisticsApi.addCrucibleRecipe(Blocks.GOLD_ORE, 25F);
		MagisticsApi.addCrucibleRecipe(Blocks.OBSIDIAN, 16F);
		MagisticsApi.addCrucibleRecipe(Blocks.DIAMOND_ORE, 64F);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}