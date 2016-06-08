package pome.waila.bcaddon.modules;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import pome.waila.bcaddon.WailaAddonBC;
import pome.waila.bcaddon.huds.HUDProviderBCPipe;

public class BCTransportModule
{
	private static Class buildCraftTransport = null;
	private static Class tileGenericPipe = null;

	public static void register()
	{
		try
		{
			buildCraftTransport = Class.forName("buildcraft.BuildCraftTransport");
		}
		catch (ClassNotFoundException e)
		{
			WailaAddonBC.log.error("Couldn't find BuildCraftTransport Class... What's wrong?");
			return;
		}
		try
		{
			tileGenericPipe = Class.forName("buildcraft.transport.TileGenericPipe");
			WailaAddonBC.log.info("TileGenericPipe class found! Registring HUD...");
		}
		catch (ClassNotFoundException e)
		{
			WailaAddonBC.log.error("Couldn't find TileGenericPipe Class... What's wrong?");
			return;
		}

		IWailaDataProvider hudProv = new HUDProviderBCPipe();

		ModuleRegistrar registrar = ModuleRegistrar.instance();
		registrar.registerStackProvider(hudProv, tileGenericPipe);
		registrar.registerHeadProvider(hudProv, tileGenericPipe);
		registrar.registerBodyProvider(hudProv, tileGenericPipe);
		registrar.registerTailProvider(hudProv, tileGenericPipe);
		WailaAddonBC.log.info("Registered successful!");
	}
}
