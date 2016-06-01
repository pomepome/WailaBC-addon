package pome.waila.bcaddon.modules;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import pome.waila.bcaddon.WailaAddonBC;
import pome.waila.bcaddon.huds.HUDProviderAutoWorkbench;

public class BCAutoWorkbenchModule
{
	public static Class autoWorkbench;
	public static Class localInventoryCrafting;

	public static Field craftMatrix;

	public static Method getRecipeOutput;

	public static void register()
	{
		try
		{
			autoWorkbench = Class.forName("buildcraft.factory.TileAutoWorkbench");
			localInventoryCrafting = Class.forName("buildcraft.factory.TileAutoWorkbench$LocalInventoryCrafting");

			getRecipeOutput = localInventoryCrafting.getDeclaredMethod("getRecipeOutput");

			craftMatrix = autoWorkbench.getDeclaredField("craftMatrix");

			IWailaDataProvider hudProv = new HUDProviderAutoWorkbench();

			ModuleRegistrar registrar = ModuleRegistrar.instance();
			registrar.registerBodyProvider(hudProv, autoWorkbench);
			registrar.registerNBTProvider(hudProv, autoWorkbench);
			WailaAddonBC.log.info("Auto Workbench module has registered successfully!");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
	}
}
