package pome.waila.bcaddon.addons;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import pome.waila.bcaddon.modules.BCAdvancedCraftingTableModule;
import pome.waila.bcaddon.modules.BCAssemblyTableModule;
import pome.waila.bcaddon.modules.BCAutoWorkbenchModule;
import pome.waila.bcaddon.modules.BCIntegrationTableModule;
import pome.waila.bcaddon.modules.BCProgrammingTableModule;
import pome.waila.bcaddon.modules.BCTransportModule;
import pome.waila.bcaddon.util.ProviderTypes;

public class AddonManager
{
	public static final Logger log = LogManager.getLogger("WailaAddonBC|AddonManager");

	public static List<Class<? extends IAddon>> registeredClasses = new ArrayList<Class<? extends IAddon>>();
	public static List<IAddon> addonList = new ArrayList<IAddon>();
	public static List<Class<? extends IAddon>> loadedAddons = new ArrayList<Class<? extends IAddon>>();

	public static void register(IAddon addon)
	{
		if(addon == null){ return; }
		Class<? extends IAddon> clazz = addon.getClass();
		if(!registeredClasses.contains(clazz))
		{
			registeredClasses.add(clazz);
			addonList.add(addon);
		}
		else
		{
			log.info("stopped loading module \""+addon.getName() + "\" because it has already registered.");
			return;
		}
		String[] missing = getMissingMods(addon);
		if(missing.length > 0)
		{
			log.info("stopped loading module \""+addon.getName() + "\" because missing required mods.");
			log.info("Missing mods: "+ generateModsString(missing));
			return;
		}
		IWailaDataProvider dataProv = addon.getNewDataProvider();
		Class classTile = addon.getTileEntityClass();

		ModuleRegistrar registrar = ModuleRegistrar.instance();
		ProviderTypes types = addon.getProvidingTypes();
		if(types.getProvidingHead())
		{
			registrar.registerHeadProvider(dataProv, classTile);
		}
		if(types.getProvidingStack())
		{
			registrar.registerStackProvider(dataProv, classTile);
		}
		if(types.getProvidingBody())
		{
			registrar.registerBodyProvider(dataProv, classTile);
		}
		if(types.getProvidingTail())
		{
			registrar.registerTailProvider(dataProv, classTile);
		}
		if(types.getProvidingNBT())
		{
			registrar.registerNBTProvider(dataProv, classTile);
		}
		loadedAddons.add(clazz);
		log.info("AddonManager has installed module \""+addon.getName()+"\" successfully.");
	}

	public static void registerDefaultAddons()
	{
		register(new BCAdvancedCraftingTableModule());
		register(new BCAssemblyTableModule());
		register(new BCAutoWorkbenchModule());
		register(new BCIntegrationTableModule());
		register(new BCProgrammingTableModule());
		register(new BCTransportModule());
	}

	public static boolean isLoaded(Class<? extends IAddon> clazz)
	{
		return loadedAddons.contains(clazz);
	}

	public static String[] getMissingMods(IAddon addon)
	{
		String[] required = addon.getRequiringMods();
		if(required == null)
		{
			return new String[]{};
		}

		List<String> list = new ArrayList<String>();
		for(String mod : required)
		{
			if(!Loader.isModLoaded(mod))
			{
				list.add(mod);
			}
		}
		return toArray(list);
	}

	public static String[] toArray(List<String> list)
	{
		String[] ret = new String[list.size()];
		for(int i = 0;i < ret.length;i++)
		{
			ret[i] = list.get(i);
		}
		return ret;
	}

	public static String generateModsString(String[] mods)
	{
		String ret = "";
		for(String mod : mods)
		{
			ret += mod + ", ";
		}
		if(ret.endsWith(", "))
		{
			ret = ret.substring(0, ret.length() - 3);
		}
		return ret;
	}
}
