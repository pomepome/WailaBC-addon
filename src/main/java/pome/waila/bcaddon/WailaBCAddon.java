package pome.waila.bcaddon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid="WailaBCAddon",name="WailaAddonBC",version = "1",dependencies = "required-after:Waila;required-after:BuildCraft|Transport@[7.0.3,)")
public class WailaBCAddon
{
	public static boolean enabled;

	public static Logger log = LogManager.getLogger("WailaBCAddon");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		enabled = config.getBoolean("enabled", "general", true, "Is the mod enabled?");
		config.save();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if(enabled)
		{
			BCTransportModule.register();
		}
	}
}
