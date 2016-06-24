package pome.waila.bcaddon.util;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameData;

public class ItemSourceFinder
{
	private static Logger logger = LogManager.getLogger("WailaAddonBC|ItemSourceFinder");
	public static String getItemOwner(ItemStack stack)
	{
		if(stack == null)
		{
			return "NULL";
		}
		ResourceLocation resource = GameData.getItemRegistry().getNameForObject(stack.getItem());
		logger.info("resource☆"+ resource);
		ModContainer mod = getModContainer(resource);
		return mod != null ? mod.getName() : "Minecraft";
	}
	public static ModContainer getModContainer(ResourceLocation location)
	{
		for(ModContainer mod : Loader.instance().getModList())
		{
			if(location.getResourceDomain().toLowerCase(Locale.US) == mod.getModId().toLowerCase(Locale.US))
			{
				return mod;
			}
		}
		return null;
	}
}
