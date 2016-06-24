package pome.waila.bcaddon;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import pome.waila.bcaddon.debugger.CommandDebug;
import pome.waila.bcaddon.ploxies.CommonProxy;

@Mod(modid="WailaAddonBC",name="WailaAddonBC",version = "1.0.0",dependencies = "required-after:Waila;after:BuildCraft|Transport@[7.0.3,);after:BuildCraft|Silicon@[7.0.3,)")
public class WailaAddonBC
{
	public static boolean enabled;

	private File confFile;

	public static Logger log = LogManager.getLogger("WailaAddonBC");

	@SidedProxy(serverSide="pome.waila.bcaddon.ploxies.CommonProxy",clientSide="pome.waila.bcaddon.ploxies.ClientProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		confFile = new File(event.getModConfigurationDirectory(),"WailaAddonBC.cfg");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Configuration config = new Configuration(confFile);
		config.load();
		enabled = config.getBoolean("enabled", "general", true, "Is the mod enabled?");
		if(enabled)
		{
			proxy.registerHUDModules();
		}
		config.save();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent e)
	{
		e.registerServerCommand(new CommandDebug());
	}

	/*
	 * Utils...
	 */
}
