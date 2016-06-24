package pome.waila.bcaddon.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;

public class TimeHolderManager
{
	private static Map<TileEntity,TimeHolder> holderMap = new HashMap<TileEntity, TimeHolder>();

	public static TimeHolder getTimeHolder(TileEntity tile)
	{
		if(holderMap.containsKey(tile))
		{
			return holderMap.get(tile);
		}
		holderMap.put(tile, new TimeHolder(System.currentTimeMillis(),0));
		return getTimeHolder(tile);
	}
}
