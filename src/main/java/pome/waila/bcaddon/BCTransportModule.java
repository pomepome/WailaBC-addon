package pome.waila.bcaddon;

import mcp.mobius.waila.api.impl.ModuleRegistrar;

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
			WailaBCAddon.log.error("Couldn't find BuildCraftTransport Class... What's wrong?");
			return;
		}
		try
		{
			tileGenericPipe = Class.forName("buildcraft.transport.TileGenericPipe");
			WailaBCAddon.log.info("TileGenericPipe class found! Registring HUD...");
		}
		catch (ClassNotFoundException e)
		{
			WailaBCAddon.log.error("Couldn't find TileGenericPipe Class... What's wrong?");
			return;
		}
		ModuleRegistrar registrar = ModuleRegistrar.instance();
		registrar.registerStackProvider(new HUDProviderBCPipe(), tileGenericPipe);
		registrar.registerHeadProvider(new HUDProviderBCPipe(), tileGenericPipe);
		registrar.registerTailProvider(new HUDProviderBCPipe(), tileGenericPipe);
		WailaBCAddon.log.info("Registered successful!");
	}
}
