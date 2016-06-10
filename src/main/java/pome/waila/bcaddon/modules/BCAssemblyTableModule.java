package pome.waila.bcaddon.modules;

import mcp.mobius.waila.api.IWailaDataProvider;
import pome.waila.bcaddon.addons.IAddon;
import pome.waila.bcaddon.huds.HUDProviderAssemblyTable;
import pome.waila.bcaddon.reflection.ReflectedObjects;
import pome.waila.bcaddon.util.ProviderTypes;

public class BCAssemblyTableModule implements IAddon
{
	@Override
	public String[] getRequiringMods()
	{
		return new String[]{"BuildCraft|Silicon"};
	}

	@Override
	public IWailaDataProvider getNewDataProvider()
	{
		return new HUDProviderAssemblyTable();
	}

	@Override
	public ProviderTypes getProvidingTypes()
	{
		return new ProviderTypes(false, false, true, false, true);
	}

	@Override
	public Class getTileEntityClass()
	{
		return ReflectedObjects.assemblyTableClass;
	}

	@Override
	public String getName()
	{
		return "AssemblyTable Module";
	}
}
