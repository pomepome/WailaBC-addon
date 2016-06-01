package pome.waila.bcaddon;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;

public class BCAdvancedCraftingTableModule
{
	public static Class advancedCraftingClass;
	public static Class laserBaseClass;

	public static Method getRequiredEnergy;
	public static Method getEnergy;
	public static Method getRecentEnergyAverage;
	public static Method canCraft;

	public static Field currentRecipe;

	public static void register()
	{
		try
		{
			advancedCraftingClass = Class.forName("buildcraft.silicon.TileAdvancedCraftingTable");
			laserBaseClass = Class.forName("buildcraft.silicon.TileLaserTableBase");
			getRequiredEnergy = advancedCraftingClass.getDeclaredMethod("getRequiredEnergy");
			getEnergy = laserBaseClass.getDeclaredMethod("getEnergy");
			getRecentEnergyAverage = advancedCraftingClass.getMethod("getRecentEnergyAverage");
			canCraft = advancedCraftingClass.getDeclaredMethod("canCraft");

			getRequiredEnergy.setAccessible(true);
			getEnergy.setAccessible(true);
			getRecentEnergyAverage.setAccessible(true);
			canCraft.setAccessible(true);

			currentRecipe = advancedCraftingClass.getDeclaredField("currentRecipe");
			currentRecipe.setAccessible(true);

			IWailaDataProvider hudProv = new HUDProviderAdvancedCraftingTable();

			ModuleRegistrar registrar = ModuleRegistrar.instance();
			registrar.registerBodyProvider(hudProv, advancedCraftingClass);
			registrar.registerNBTProvider(hudProv, advancedCraftingClass);
			WailaAddonBC.log.info("Advanced Crafting Table module has registered successfully!");
		}
		catch(ClassNotFoundException e)
		{
			WailaAddonBC.log.error("Advanced Crafting Table class was not found...");
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
