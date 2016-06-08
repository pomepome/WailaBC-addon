package pome.waila.bcaddon;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import buildcraft.api.boards.RedstoneBoardNBT;
import buildcraft.api.boards.RedstoneBoardRegistry;
import buildcraft.robotics.ItemRedstoneBoard;
import buildcraft.robotics.ItemRobot;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import pome.waila.bcaddon.debugger.CommandDebug;
import pome.waila.bcaddon.ploxies.CommonProxy;

@Mod(modid="WailaAddonBC",name="WailaAddonBC",version = "1.0.1",dependencies = "required-after:Waila;required-after:BuildCraft|Transport@[7.0.3,);required-after:BuildCraft|Silicon@[7.0.3,)")
public class WailaAddonBC
{
	public static boolean enabled;

	private File confFile;

	public static Logger log = LogManager.getLogger("WailaAddonBC");

	@SidedProxy(serverSide="pome.waila.bcaddon.ploxies.CommonProxy",clientSide="pome.waila.bcaddon.ploxies.ClientProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		confFile = new File(event.getModConfigurationDirectory(),"WailaAddonBC.cfg");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Configuration config = new Configuration(confFile);
		config.load();
		enabled = config.getBoolean("enabled", "general", true, "Is the mod enabled?");
		if(enabled)
		{
			proxy.registerHUDModules();
		}
		config.save();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent e)
	{
		e.registerServerCommand(new CommandDebug());
	}

	public static <T> T getFieldValue(Field f,Object obj)
	{
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
	public static String formatTime(double sec)
	{
		if(Double.isInfinite(sec) || Double.isNaN(sec))
		{
			return "0.0s";
		}
		String res = "";
		if(sec >= 3600)
		{
			res += ((int)sec / 3600) + "h";
			sec -= ((int)sec / 3600)*3600;
		}
		if(sec >= 60)
		{
			res += ((int)sec / 60) + "m";
			sec -= ((int)sec / 60)*60;
		}
		if(sec > 0)
		{
			BigDecimal bd = new BigDecimal(sec);
			bd = bd.setScale(1, RoundingMode.DOWN);
			res += bd.toString()+"s";
		}
		return res;
	}
	public static void removeTag(NBTTagCompound nbt,String tag)
	{
		if(nbt.hasKey(tag))
		{
			nbt.removeTag(tag);
		}
	}
	public static String getRedstoneBoardName(ItemStack stack)
	{
		if(stack == null || !stack.hasTagCompound() || !(stack.getItem() instanceof ItemRobot || stack.getItem() instanceof ItemRedstoneBoard))
		{
			return "UNKNOWN";
		}
		RedstoneBoardNBT rbNBT = RedstoneBoardRegistry.instance.getRedstoneBoard(stack.stackTagCompound);
		if(rbNBT == null)
		{
			return "UNKNOWN";
		}
		if(rbNBT.getID() == RedstoneBoardRegistry.instance.getEmptyRobotBoard().getID())
		{
			return "empty";
		}
		if(stack.getItem() instanceof ItemRobot)
		{
			return getBCBNBT(stack,ItemRobot.getRobotNBT(stack));
		}
		return getBCBNBT(stack, rbNBT);
	}
	public static String getBCBNBT(ItemStack stack,RedstoneBoardNBT bcbn)
	{
		List<String> list = new ArrayList<String>();
		bcbn.addInformation(stack, null, list, false);
		return list.get(0);
	}
	public static <T> List<T> copyList(List<T> source,int min)
	{
		List<T> list = new ArrayList<T>();
		for(int i = min;i < source.size();i++)
		{
			list.add(source.get(i));
		}
		return list;
	}
}
