package pome.waila.bcaddon.modules;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import pome.waila.bcaddon.ReflectionHelper;
import pome.waila.bcaddon.WailaAddonBC;
import pome.waila.bcaddon.huds.HUDProviderIntegrationTable;

public class BCIntegrationTableModule
{
	public static Class integrationTableClass;
	public static Class laserBaseClass;
	public static Class itemGate;

	public static Method getRequiredEnergy;
	public static Method getEnergy;
	public static Method canCraft;
	public static Method getStackInSlot;
	public static Method getExpansions;
	public static Method getInstalledExpansions;

	public static Field activeRecipe;

	public static void register()
	{
		try
		{
			integrationTableClass = Class.forName("buildcraft.silicon.TileIntegrationTable");
			laserBaseClass = Class.forName("buildcraft.silicon.TileLaserTableBase");
			itemGate = Class.forName("buildcraft.transport.gates.ItemGate");

			getRequiredEnergy = integrationTableClass.getDeclaredMethod("getRequiredEnergy");
			getEnergy = laserBaseClass.getDeclaredMethod("getEnergy");
			canCraft = integrationTableClass.getDeclaredMethod("canCraft");
			getExpansions = integrationTableClass.getDeclaredMethod("getExpansions");
			getStackInSlot = ReflectionHelper.getMethod(IInventory.class, "getStackInSlot", "func_70301_a", int.class);
			getInstalledExpansions = itemGate.getMethod("getInstalledExpansions", ItemStack.class);
			getRequiredEnergy.setAccessible(true);
			getEnergy.setAccessible(true);
			canCraft.setAccessible(true);
			getExpansions.setAccessible(true);

			activeRecipe = integrationTableClass.getDeclaredField("activeRecipe");
			activeRecipe.setAccessible(true);

			IWailaDataProvider hudProv = new HUDProviderIntegrationTable();

			ModuleRegistrar registrar = ModuleRegistrar.instance();
			registrar.registerBodyProvider(hudProv, integrationTableClass);
			registrar.registerNBTProvider(hudProv, integrationTableClass);
			WailaAddonBC.log.info("Integration Table module has registered successfully!");
		}
		catch(ClassNotFoundException e)
		{
			WailaAddonBC.log.error("Integration Table class was not found...");
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

