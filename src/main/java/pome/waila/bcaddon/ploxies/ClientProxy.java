package pome.waila.bcaddon.ploxies;

import pome.waila.bcaddon.addons.AddonManager;
import pome.waila.bcaddon.reflection.ReflectedObjects;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerHUDModules()
	{
		ReflectedObjects.load();
		AddonManager.registerDefaultAddons();
	}
}
