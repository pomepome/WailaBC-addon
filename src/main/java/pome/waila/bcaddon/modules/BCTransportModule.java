package pome.waila.bcaddon.modules;

import mcp.mobius.waila.api.IWailaDataProvider;
import pome.waila.bcaddon.addons.IAddon;
import pome.waila.bcaddon.huds.HUDProviderBCPipe;
import pome.waila.bcaddon.reflection.ReflectedObjects;
import pome.waila.bcaddon.util.ProviderTypes;

public class BCTransportModule implements IAddon
{

	@Override
	public String[] getRequiringMods()
	{
		return new String[]{"BuildCraft|Transport"};
	}

	@Override
	public IWailaDataProvider getNewDataProvider()
	{
		return new HUDProviderBCPipe();
	}

	@Override
	public ProviderTypes getProvidingTypes()
	{
		return new ProviderTypes(true, true, true, true, false);
	}

	@Override
	public Class getTileEntityClass()
	{
		return ReflectedObjects.pipeTileClass;
	}

	@Override
	public String getName()
	{
		return "Pipe Module";
	}
}
