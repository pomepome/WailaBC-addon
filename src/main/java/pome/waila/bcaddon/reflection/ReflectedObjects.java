package pome.waila.bcaddon.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ReflectedObjects
{
	/**
	 * Classes Section
	 */

	//tiles
	public static Class advancedCraftingTableClass = null;
	public static Class assemblyTableClass = null;
	public static Class autoWorkbenchClass = null;
	public static Class integrationTableClass = null;
	public static Class programmingTableClass = null;
	public static Class pipeTileClass = null;
	public static Class laserBaseClass = null;

	//items
	public static Class itemGateClass = null;

	//inventories
	public static Class inventoryClass = null;
	public static Class localInventoryCrafting = null;

	//managers
	public static Class assemblyRecipeManager = null;

	/**
	 * Methods Section
	 */

	//IInventory
	public static Method getStackInSlot = null;

	//TileLaserTableBase
	public static Method getRequiredEnergy = null;
	public static Method getEnergy = null;
	public static Method canCraft = null;

	//TileIntegrationTable
	public static Method getExpansions;

	//ItemGate
	public static Method getInstalledExpansions;

	//LocalInventoryCrafting
	public static Method getRecipeOutput = null;

	/**
	 * Fields Section
	 */

	//TileProgrammingTable
	public static Field optionId;
	public static Field options;

	//TileIntegrationTable
	public static Field activeRecipe;

	//TileAutoWorkbench
	public static Field craftMatrix;

	//AssemblyRecipeManager
	public static Field recipeManager;

	/**
	 * Loading methods
	 */
	public static void load()
	{
		loadClasses();
		loadMethods();
		loadFields();
	}
	public static void loadClasses()
	{
		inventoryClass = IInventory.class;
		advancedCraftingTableClass = tryLoadClass("buildcraft.silicon.TileAdvancedCraftingTable");
		assemblyTableClass = tryLoadClass("buildcraft.silicon.TileAssemblyTable");
		integrationTableClass = tryLoadClass("buildcraft.silicon.TileIntegrationTable");
		programmingTableClass = tryLoadClass("buildcraft.silicon.TileProgrammingTable");
		autoWorkbenchClass = tryLoadClass("buildcraft.factory.TileAutoWorkbench");
		pipeTileClass = tryLoadClass("buildcraft.transport.TileGenericPipe");
		laserBaseClass = tryLoadClass("buildcraft.silicon.TileLaserTableBase");

		itemGateClass = tryLoadClass("buildcraft.transport.gates.ItemGate");

		localInventoryCrafting = tryLoadClass("buildcraft.factory.TileAutoWorkbench$LocalInventoryCrafting");

		assemblyRecipeManager = tryLoadClass("buildcraft.core.recipes.AssemblyRecipeManager");
	}

	public static void loadMethods()
	{
		getStackInSlot = ReflectionHelper.getMethod(IInventory.class, "getStackInSlot", "func_70301_a", int.class);

		getRequiredEnergy = tryLoadMethod(laserBaseClass,"getRequiredEnergy");
		getEnergy = tryLoadMethod(laserBaseClass,"getEnergy");
		canCraft = tryLoadMethod(laserBaseClass,"canCraft");

		getExpansions = tryLoadMethod(integrationTableClass, "getExpansions");

		getInstalledExpansions = tryLoadMethod(itemGateClass, "getInstalledExpansions", ItemStack.class);

		getRecipeOutput = tryLoadMethod(localInventoryCrafting, "getRecipeOutput");
	}

	public static void loadFields()
	{
		optionId = tryLoadField(programmingTableClass, "optionId");
		options = tryLoadField(programmingTableClass, "options");

		activeRecipe = tryLoadField(integrationTableClass,"activeRecipe");

		craftMatrix = tryLoadField(autoWorkbenchClass, "craftMatrix");

		recipeManager = tryLoadField(assemblyRecipeManager, "INSTANCE");
	}

	public static Class tryLoadClass(String path)
	{
		Class c = null;
		try
		{
			c = Class.forName(path);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}

	public static Method tryLoadMethod(Class clazz,String name,Class... parameterTypes)
	{
		if(clazz == null)
		{
			return null;
		}
		Method m = null;
		try
		{
			m = clazz.getDeclaredMethod(name, parameterTypes);
			m.setAccessible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return m;
	}

	public static Field tryLoadField(Class clazz,String name)
	{
		if(clazz == null)
		{
			return null;
		}
		Field m = null;
		try
		{
			m = clazz.getDeclaredField(name);
			m.setAccessible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return m;
	}

	public static <T> T tryGetCurrentRecipe(Object obj)
	{
		try
		{
			Field f = obj.getClass().getDeclaredField("currentRecipe");
			f.setAccessible(true);
			return getFieldValue(f,obj);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static <T> T getFieldValue(Field f,Object obj)
	{
		if(f == null || obj == null)
		{
			return null;
		}
		try
		{
			return (T)f.get(obj);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	public static <T> T Invoke(Method m,Object obj,Object... args)
	{
		if(m == null || obj == null)
		{
			return null;
		}
		try
		{
			return (T)m.invoke(obj, args);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
