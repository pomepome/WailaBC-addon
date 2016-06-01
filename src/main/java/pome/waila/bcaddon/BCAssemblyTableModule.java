package pome.waila.bcaddon;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mcp.mobius.waila.api.impl.ModuleRegistrar;

public class BCAssemblyTableModule
{
	public static Class assemblyTableClass;
	public static Class laserBaseClass;
	public static Class assemblyRecipeManager;

	public static Method getRequiredEnergy;
	public static Method getEnergy;
	public static Method getRecentEnergyAverage;
	public static Method canCraft;

	public static Field currentRecipe;
	public static Field recipeManager;

	public static void register()
	{
		try
		{
			assemblyTableClass = Class.forName("buildcraft.silicon.TileAssemblyTable");
			laserBaseClass = Class.forName("buildcraft.silicon.TileLaserTableBase");
			assemblyRecipeManager = Class.forName("buildcraft.core.recipes.AssemblyRecipeManager");

			getRequiredEnergy = assemblyTableClass.getDeclaredMethod("getRequiredEnergy");
			getEnergy = laserBaseClass.getDeclaredMethod("getEnergy");
			getRecentEnergyAverage = assemblyTableClass.getMethod("getRecentEnergyAverage");
			canCraft = assemblyTableClass.getDeclaredMethod("canCraft");

			getRequiredEnergy.setAccessible(true);
			getEnergy.setAccessible(true);
			getRecentEnergyAverage.setAccessible(true);
			canCraft.setAccessible(true);

			currentRecipe = assemblyTableClass.getDeclaredField("currentRecipe");
			currentRecipe.setAccessible(true);
			recipeManager = assemblyRecipeManager.getDeclaredField("INSTANCE");

			HUDProviderAssemblyTable hudProv = new HUDProviderAssemblyTable();

			ModuleRegistrar registrar = ModuleRegistrar.instance();
			registrar.registerBodyProvider(hudProv, assemblyTableClass);
			registrar.registerNBTProvider(hudProv, assemblyTableClass);
			WailaAddonBC.log.info("Assembly table module has registered successfully!");
		}
		catch(ClassNotFoundException e)
		{
			WailaAddonBC.log.error("Assembly table class was not found...");
		}
		catch(NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
	}
}
