package pome.waila.bcaddon;

import java.util.ArrayList;
import java.util.List;

import buildcraft.transport.TileGenericPipe;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HUDProviderBCPipe implements IWailaDataProvider
{
	private static final String prefixModName = "\u00a79\u00a7o";//イタリック青字

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		TileEntity tile = accessor.getTileEntity();
		if(tile instanceof TileGenericPipe)
		{
			Item pipeItem = ((TileGenericPipe)tile).pipe.item;//パイプのアイテム取得
			return new ItemStack(pipeItem);
		}
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack stack, List<String> defaulttip, IWailaDataAccessor accessor,IWailaConfigHandler config)
	{
		defaulttip = new ArrayList<String>();
		TileEntity tile = accessor.getTileEntity();
		if(tile instanceof TileGenericPipe)
		{
			Item pipeItem = ((TileGenericPipe)tile).pipe.item;//パイプのアイテム取得
			defaulttip.add( SpecialChars.WHITE + (new ItemStack(pipeItem).getDisplayName()));
		}
		return defaulttip;
	}


	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> defaulttip, IWailaDataAccessor accessor,IWailaConfigHandler config)
	{
		return defaulttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack stack, List<String> defaulttip, IWailaDataAccessor accessor,IWailaConfigHandler config)
	{
		defaulttip = new ArrayList<String>();
		TileEntity tile = accessor.getTileEntity();
		if(tile instanceof TileGenericPipe)
		{
			Item pipeItem = ((TileGenericPipe)tile).pipe.item;//パイプのアイテム取得
			defaulttip.add(prefixModName + ModIdentification.nameFromStack(new ItemStack(pipeItem)));
		}
		return defaulttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound nbt, World world, int x,int y, int z)
	{
		return null;
	}

}
