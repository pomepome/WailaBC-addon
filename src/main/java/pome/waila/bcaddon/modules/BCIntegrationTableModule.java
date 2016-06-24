package pome.waila.bcaddon.modules;

import mcp.mobius.waila.api.IWailaDataProvider;
import pome.waila.bcaddon.addons.IAddon;
import pome.waila.bcaddon.huds.HUDProviderIntegrationTable;
import pome.waila.bcaddon.reflection.ReflectedObjects;
import pome.waila.bcaddon.util.ProviderTypes;

public class BCIntegrationTableModule implements IAddon
{

	@Override
	public String[] getRequiringMods()
	{
		return new String[]{"BuildCraft|Silicon"};
	}

	@Override
	public IWailaDataProvider getNewDataProvider()
	{
		return new HUDProviderIntegrationTable();
	}

	@Override
	public ProviderTypes getProvidingTypes()
	{
		return new ProviderTypes(false, false, true, false, true);
	}

	@Override
	public Class getTileEntityClass()
	{
		return ReflectedObjects.integrationTableClass;
	}

	@Override
	public String getName()
	{
		return "IntegrationTable Module";
	}
}

