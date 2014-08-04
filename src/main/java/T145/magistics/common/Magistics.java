package T145.magistics.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import T145.magistics.common.config.Config;
import T145.magistics.net.UniversalProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Magistics.modid, name = Magistics.modid, version = Magistics.version)
public class Magistics {
	public static final String modid = "Magistics", version = "0.4.3", proxyPath = "T145.magistics.net.UniversalProxy";

	@Instance(modid)
	public static Magistics instance;

	@SidedProxy(clientSide = proxyPath, serverSide = proxyPath)
	public static UniversalProxy proxy;

	public static Logger logger = LogManager.getLogger(modid);

	public static CreativeTabs tabMagistics = new CreativeTabs(modid) {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.anvil);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Config.preInit(e.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		Config.init();
		proxy.registerRenderInformation();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		Config.postInit();
	}
}