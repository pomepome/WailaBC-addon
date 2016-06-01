package pome.waila.bcaddon;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import net.minecraft.inventory.IInventory;

public class BCProgrammingTableModule
{
	public static Class programmingTableClass;
	public static Class laserBaseClass;
	public static Class redstoneBoardRegistry;
	public static Class bCBoardNBT;

	public static Method getRequiredEnergy;
	public static Method getEnergy;
	public static Method getRecentEnergyAverage;
	public static Method canCraft;
	public static Method getStackInSlot;

	public static Field currentRecipe;
	public static Field optionId;
	public static Field options;
	public static Field INSTANCE;
	public static Field upperName;

	public static void register()
	{
		try
		{
			debug();
			programmingTableClass = Class.forName("buildcraft.silicon.TileProgrammingTable");
			laserBaseClass = Class.forName("buildcraft.silicon.TileLaserTableBase");
			redstoneBoardRegistry = Class.forName("buildcraft.api.boards.RedstoneBoardRegistry");
			bCBoardNBT = Class.forName("buildcraft.robotics.boards.BCBoardNBT");

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
			INSTANCE = redstoneBoardRegistry.getDeclaredField("instance");
			INSTANCE.setAccessible(true);
			upperName = bCBoardNBT.getDeclaredField("upperName");
			upperName.setAccessible(true);

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
	public static void debug()
	{
/*
		WailaAddonBC.log.info("METHOD NAME;INDEX;RETURN VAL;ARGS VAL");
		Method[] methods = IInventory.class.getDeclaredMethods();
		for(int i = 0;i < methods.length;i++)
		{
			Method m = methods[i];
			WailaAddonBC.log.info(m.getName()+";"+i+";"+m.getReturnType().getName()+";");
			for(Class c : m.getParameterTypes())
			{
				WailaAddonBC.log.info(c.getName());
			}
		}*/
	}
}
