package T145.magistics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import T145.magistics.lib.CreativeTabMagistics;
import T145.magistics.network.CommonProxy;
import T145.magistics.network.FMLProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Magistics.MODID, name = Magistics.NAME, version = Magistics.VERSION)
public class Magistics extends FMLProxy {

	public static final String MODID = "magistics";
	public static final String NAME = "Magistics";
	public static final String VERSION = "$version";
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	public static final CreativeTabs TAB = new CreativeTabMagistics();

	@Instance(MODID)
	public static Magistics instance;

	@SidedProxy(serverSide = "T145.magistics.network.CommonProxy", clientSide = "T145.magistics.network.ClientProxy")
	public static CommonProxy proxy;

	@Metadata
	private ModMetadata meta;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		LOGGER.info("Hello World!");

		meta.authorList.add("T145");
		meta.autogenerated = false;
		meta.credits = "Thanks to everyone who kept believing!";
		meta.description = "Logistical magic!";
		meta.logoFile = "logo.png";
		meta.modId = MODID;
		meta.name = NAME;
		meta.url = "https://github.com/T145/magistics";
		meta.useDependencyInformation = false;
		meta.version = VERSION;

		proxy.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}