package pome.waila.bcaddon.modules;

import mcp.mobius.waila.api.IWailaDataProvider;
import pome.waila.bcaddon.addons.IAddon;
import pome.waila.bcaddon.huds.HUDProviderProgrammingTable;
import pome.waila.bcaddon.reflection.ReflectedObjects;
import pome.waila.bcaddon.util.ProviderTypes;

public class BCProgrammingTableModule implements IAddon
{

	@Override
	public String[] getRequiringMods()
	{
		return new String[]{"BuildCraft|Silicon"};
	}

	@Override
	public IWailaDataProvider getNewDataProvider()
	{
		return new HUDProviderProgrammingTable();
	}

	@Override
	public ProviderTypes getProvidingTypes()
	{
		return new ProviderTypes(false, false, true, false, true);
	}

	@Override
	public Class getTileEntityClass()
	{
		return ReflectedObjects.programmingTableClass;
	}

	@Override
	public String getName()
	{
		return "ProgrammingTable Module";
	}
}
