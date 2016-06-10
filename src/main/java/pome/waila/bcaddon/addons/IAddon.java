package pome.waila.bcaddon.addons;

import mcp.mobius.waila.api.IWailaDataProvider;
import pome.waila.bcaddon.util.ProviderTypes;

public interface IAddon
{
	String[] getRequiringMods();
	IWailaDataProvider getNewDataProvider();
	ProviderTypes getProvidingTypes();
	Class getTileEntityClass();
	String getName();
}
