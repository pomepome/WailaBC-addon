package pome.waila.bcaddon.huds;

import static pome.waila.bcaddon.reflection.ReflectedObjects.*;
import static pome.waila.bcaddon.util.Utils.*;

import java.util.List;

import buildcraft.api.recipes.IProgrammingRecipe;
import buildcraft.silicon.TileProgrammingTable;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class HUDProviderProgrammingTable implements IWailaDataProvider
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
		if(tile instanceof TileProgrammingTable)
		{
			TileProgrammingTable table = (TileProgrammingTable)tile;
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
		if(tile instanceof TileProgrammingTable)
		{
			TileProgrammingTable table = (TileProgrammingTable)tile;

			double estTime = predictRemTime(table);
			nbt.setDouble("estTime", estTime);

			boolean canCrafting = Invoke(canCraft,table);
			nbt.setBoolean("canCraft", canCrafting);

			removeTag(nbt,"type");
			removeTag(nbt, "content");

			IProgrammingRecipe current = tryGetCurrentRecipe(table);
			if(current != null)
			{
				int optionIndex = getFieldValue(optionId, table);

				List<ItemStack> ops = getFieldValue(options,table);
				if(ops != null && optionIndex >= 0 && optionIndex < ops.size())
				{
					ItemStack first = Invoke(getStackInSlot,table,0);
					ItemStack second = ops.get(optionIndex);
					ItemStack output = current.craft(first, second).copy();

					writeStackToNBT(nbt, "content", output);

					String type = getRedstoneBoardName(output);
					if(type != "UNKNOWN")
					{
						nbt.setString("type", type);
					}
				}
			}
		}
		return nbt;
	}
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world,BlockPos pos)
	{
		return getNBTData(player, tile, tag, world, pos.getX(), pos.getY(), pos.getZ());
	}
}
