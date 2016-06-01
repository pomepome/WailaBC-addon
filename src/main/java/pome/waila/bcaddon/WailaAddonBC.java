package pome.waila.bcaddon;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;

@Mod(modid="WailaAddonBC",name="WailaAddonBC",version = "1.0.1",dependencies = "required-after:Waila;required-after:BuildCraft|Transport@[7.0.3,);required-after:BuildCraft|Silicon@[7.0.3,)")
public class WailaAddonBC
{
	public static boolean enabled;

	private File confFile;

	public static Logger log = LogManager.getLogger("WailaAddonBC");

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
			BCTransportModule.register();
			BCAssemblyTableModule.register();
			BCAdvancedCraftingTableModule.register();
			BCProgrammingTableModule.register();
			BCIntegrationTableModule.register();
			BCAutoWorkbenchModule.register();
		}
		config.save();
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
}
