package pome.waila.bcaddon.ploxies;

import pome.waila.bcaddon.modules.BCAdvancedCraftingTableModule;
import pome.waila.bcaddon.modules.BCAssemblyTableModule;
import pome.waila.bcaddon.modules.BCAutoWorkbenchModule;
import pome.waila.bcaddon.modules.BCIntegrationTableModule;
import pome.waila.bcaddon.modules.BCProgrammingTableModule;
import pome.waila.bcaddon.modules.BCTransportModule;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerHUDModules()
	{
		BCTransportModule.register();
		BCAssemblyTableModule.register();
		BCAdvancedCraftingTableModule.register();
		BCProgrammingTableModule.register();
		BCIntegrationTableModule.register();
		BCAutoWorkbenchModule.register();
	}
}
