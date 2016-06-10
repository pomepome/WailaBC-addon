package pome.waila.bcaddon.huds;

import static pome.waila.bcaddon.reflection.ReflectedObjects.*;
import static pome.waila.bcaddon.util.Utils.*;

import java.util.List;

import buildcraft.silicon.TileAdvancedCraftingTable;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import pome.waila.bcaddon.util.TimeHolder;

public class HUDProviderAdvancedCraftingTable implements IWailaDataProvider
{
	private TimeHolder timeHolder = new TimeHolder(0,0);

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
		if(tile instanceof TileAdvancedCraftingTable)
		{
			TileAdvancedCraftingTable table = (TileAdvancedCraftingTable)tile;
			NBTTagCompound tag = accessor.getNBTData();
			if(tag.hasKey("content"))
			{
				ItemStack result = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("content"));
				defaulttip.add( EnumChatFormatting.BLUE + "out: " + EnumChatFormatting.RESET + formatString(result));
			}
			else
			{
				defaulttip.add(EnumChatFormatting.BLUE + "out: " + EnumChatFormatting.RESET +"NULL");
			}
			if(tag.hasKey("type"))
			{
				defaulttip.add(EnumChatFormatting.BLUE + "Type: " + EnumChatFormatting.RESET + tag.getString("type"));
			}
			if(tag.getBoolean("canCraft"))
			{
				defaulttip.add(EnumChatFormatting.BLUE + "est. time: " + EnumChatFormatting.RESET + formatTime(tag.getDouble("estTime")));
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
		if(tile instanceof TileAdvancedCraftingTable)
		{
			TileAdvancedCraftingTable table = (TileAdvancedCraftingTable)tile;

			double estTime = predictRemTime(table,timeHolder);
			nbt.setDouble("estTime", estTime);

			boolean canCrafting = Invoke(canCraft,table);
			nbt.setBoolean("canCraft", canCrafting);

			removeTag(nbt,"content");
			removeTag(nbt,"type");

			IRecipe current = tryGetCurrentRecipe(table);
			if(current != null)
			{
				ItemStack output = current.getRecipeOutput().copy();

				NBTTagCompound subNBT = new NBTTagCompound();
				output.writeToNBT(subNBT);

				nbt.setTag("content", subNBT);

				String type = getRedstoneBoardName(output);
				if(type != "UNKNOWN")
				{
					nbt.setString("type", type);
				}
			}
		}
		return nbt;
	}
}
