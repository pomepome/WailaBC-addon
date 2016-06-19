package pome.waila.bcaddon.huds;

import static pome.waila.bcaddon.reflection.ReflectedObjects.*;
import static pome.waila.bcaddon.util.Utils.*;

import java.util.List;

import buildcraft.factory.TileAutoWorkbench;
import buildcraft.factory.TileAutoWorkbench.LocalInventoryCrafting;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class HUDProviderAutoWorkbench implements IWailaDataProvider
{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack stack, List<String> defaulttip, IWailaDataAccessor accessor,IWailaConfigHandler config)
	{
		return defaulttip;
	}


	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> defaulttip, IWailaDataAccessor accessor,IWailaConfigHandler config)
	{
		TileEntity tile = accessor.getTileEntity();
		if(tile instanceof TileAutoWorkbench)
		{
			TileAutoWorkbench table = (TileAutoWorkbench)tile;
			NBTTagCompound tag = accessor.getNBTData();
			if(tag.hasKey("content"))
			{
				ItemStack output = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("content"));
				defaulttip.add(String.format("%s%s %s%s", EnumChatFormatting.BLUE,translate(resultString),EnumChatFormatting.RESET,formatString(output)));
			}
			else
			{
				defaulttip.add(String.format("%s%s %sNULL", EnumChatFormatting.BLUE,translate(resultString),EnumChatFormatting.RESET));
			}
			if(tag.hasKey("type"))
			{
				defaulttip.add(String.format("%s%s %s%s", EnumChatFormatting.BLUE,translate(typeString),EnumChatFormatting.RESET,tag.getString("type")));
			}
			if(tag.getBoolean("canCraft"))
			{
				defaulttip.add(String.format("%s%s %s%s", EnumChatFormatting.BLUE,translate(timeString),EnumChatFormatting.RESET,formatTime(tag.getDouble("estTime"))));
			}
		}
		return defaulttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack stack, List<String> defaulttip, IWailaDataAccessor accessor,IWailaConfigHandler config)
	{
		return defaulttip;
	}

	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound nbt, World world, int x,int y, int z)
	{
		if(tile instanceof TileAutoWorkbench)
		{
			TileAutoWorkbench table = (TileAutoWorkbench)tile;

			removeTag(nbt,"content");
			removeTag(nbt,"type");

			LocalInventoryCrafting matrix = getFieldValue(craftMatrix, table);
			ItemStack current = Invoke(getRecipeOutput, matrix);

			if(current != null)
			{
				writeStackToNBT(nbt, "content", current);

				String type = getRedstoneBoardName(current);
				if(type != "UNKNOWN")
				{
					nbt.setString("type", type);
				}
			}
		}
		return nbt;
	}
}
