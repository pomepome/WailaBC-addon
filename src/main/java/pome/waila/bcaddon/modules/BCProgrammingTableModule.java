package pome.waila.bcaddon.modules;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import net.minecraft.inventory.IInventory;
import pome.waila.bcaddon.ReflectionHelper;
import pome.waila.bcaddon.WailaAddonBC;
import pome.waila.bcaddon.huds.HUDProviderProgrammingTable;

public class BCProgrammingTableModule
{
	public static Class programmingTableClass;
	public static Class laserBaseClass;

	public static Method getRequiredEnergy;
	public static Method getEnergy;
	public static Method getRecentEnergyAverage;
	public static Method canCraft;
	public static Method getStackInSlot;

	public static Field currentRecipe;
	public static Field optionId;
	public static Field options;

	public static void register()
	{
		try
		{
			programmingTableClass = Class.forName("buildcraft.silicon.TileProgrammingTable");
			laserBaseClass = Class.forName("buildcraft.silicon.TileLaserTableBase");
			getRequiredEnergy = programmingTableClass.getDeclaredMethod("getRequiredEnergy");
			getEnergy = laserBaseClass.getDeclaredMethod("getEnergy");
			getRecentEnergyAverage = programmingTableClass.getMethod("getRecentEnergyAverage");
			canCraft = programmingTableClass.getDeclaredMethod("canCraft");
			getStackInSlot = ReflectionHelper.getMethod(IInventory.class, "getStackInSlot", "func_70301_a", int.class);

			getRequiredEnergy.setAccessible(true);
			getEnergy.setAccessible(true);
			getRecentEnergyAverage.setAccessible(true);
			canCraft.setAccessible(true);

			currentRecipe = programmingTableClass.getDeclaredField("currentRecipe");
			currentRecipe.setAccessible(true);
			optionId = programmingTableClass.getDeclaredField("optionId");
			optionId.setAccessible(true);
			options = programmingTableClass.getDeclaredField("options");
			options.setAccessible(true);

			IWailaDataProvider hudProv = new HUDProviderProgrammingTable();

			ModuleRegistrar registrar = ModuleRegistrar.instance();
			registrar.registerBodyProvider(hudProv, programmingTableClass);
			registrar.registerNBTProvider(hudProv, programmingTableClass);
			WailaAddonBC.log.info("Programming Table module has registered successfully!");
		}
		catch(ClassNotFoundException e)
		{
			WailaAddonBC.log.error("Programming Table class was not found...");
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
