package pome.waila.bcaddon.util;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.ItemStack;

public class ItemSourceFinder
{
	private static Logger logger = LogManager.getLogger("WailaAddonBC|ItemSourceFinder");
	public static String getItemOwner(ItemStack stack)
	{
		if(stack == null)
		{
			return "NULL";
		}
		String resource = GameData.getItemRegistry().getNameForObject(stack.getItem()).split(":")[0];
		logger.info("resource☆"+ resource);
		ModContainer mod = getModContainer(resource);
		return mod != null ? mod.getName() : "Minecraft";
	}
	public static ModContainer getModContainer(String modId)
	{
		for(ModContainer mod : Loader.instance().getModList())
		{
			if(modId.toLowerCase(Locale.US) == mod.getModId().toLowerCase(Locale.US))
			{
				return mod;
			}
		}
		return null;
	}
}
