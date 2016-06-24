package pome.waila.bcaddon.modules;

import mcp.mobius.waila.api.IWailaDataProvider;
import pome.waila.bcaddon.addons.IAddon;
import pome.waila.bcaddon.huds.HUDProviderAutoWorkbench;
import pome.waila.bcaddon.reflection.ReflectedObjects;
import pome.waila.bcaddon.util.ProviderTypes;

public class BCAutoWorkbenchModule implements IAddon
{

	@Override
	public String[] getRequiringMods()
	{
		return new String[]{"BuildCraft|Factory"};
	}

	@Override
	public IWailaDataProvider getNewDataProvider()
	{
		return new HUDProviderAutoWorkbench();
	}

	@Override
	public ProviderTypes getProvidingTypes()
	{
		return new ProviderTypes(false, false, true, false, true);
	}

	@Override
	public Class getTileEntityClass()
	{
		return ReflectedObjects.autoWorkbenchClass;
	}

	@Override
	public String getName()
	{
		return "AutoWorkbench Module";
	}
}
