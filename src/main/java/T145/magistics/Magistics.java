package T145.magistics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import T145.magistics.core.ConfigMain;
import T145.magistics.lib.CommandMagistics;
import T145.magistics.lib.CreativeTabMagistics;
import T145.magistics.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Magistics.MODID, name = Magistics.NAME, version = Magistics.VERSION, guiFactory = "T145.magistics.client.gui.config.GuiFactoryMagistics")
public class Magistics {

	public static final String MODID = "magistics";
	public static final String NAME = "Magistics";
	public static final String VERSION = "$version";
	public static final Logger LOG = LogManager.getLogger(NAME);
	public static final CreativeTabs TAB = new CreativeTabMagistics();
	public static final ConfigMain CONFIG = new ConfigMain();

	@Instance(MODID)
	public static Magistics instance;

	@SidedProxy(serverSide = "T145.magistics.proxies.CommonProxy", clientSide = "T145.magistics.proxies.ClientProxy")
	public static CommonProxy proxy;

	@Metadata
	private ModMetadata meta;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
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

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

		CONFIG.load(event);
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandMagistics());
	}
}