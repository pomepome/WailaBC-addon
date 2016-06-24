package pome.waila.bcaddon.huds;

import static pome.waila.bcaddon.reflection.ReflectedObjects.*;
import static pome.waila.bcaddon.util.Utils.*;

import java.util.List;
import java.util.Set;

import buildcraft.api.gates.IGateExpansion;
import buildcraft.api.recipes.IIntegrationRecipe;
import buildcraft.silicon.TileIntegrationTable;
import buildcraft.transport.gates.ItemGate;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class HUDProviderIntegrationTable implements IWailaDataProvider
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
		if(tile instanceof TileIntegrationTable)
		{
			TileIntegrationTable table = (TileIntegrationTable)tile;
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
			if(tag.hasKey("expansions") && tag.getTagList("expansions",8).tagCount() > 0)
			{
				NBTTagList list = tag.getTagList("expansions", 8);
				defaulttip.add(EnumChatFormatting.BLUE + translate("hud.exp") + " ");
				for(int i = 0;i < list.tagCount();i++)
				{
					defaulttip.add(list.getStringTagAt(i));
				}
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
		if(tile instanceof TileIntegrationTable)
		{
			TileIntegrationTable table = (TileIntegrationTable)tile;

			double estTime = predictRemTime(table);
			nbt.setDouble("estTime", estTime);

			boolean canCrafting = Invoke(canCraft,table);
			nbt.setBoolean("canCraft", canCrafting);

			removeTag(nbt,"content");
			removeTag(nbt,"type");
			removeTag(nbt,"expansions");

			IIntegrationRecipe current = getFieldValue(activeRecipe, table);
			if(current != null)
			{
				ItemStack first = Invoke(getStackInSlot,table,0);
				List<ItemStack> expansions = Invoke(getExpansions,table);

				ItemStack output = current.craft(first, expansions, false).copy();

				if(output != null)
				{
					writeStackToNBT(nbt, "content", output);

					String str = getRedstoneBoardName(output);
					if(str != "UNKNOWN")
					{
						nbt.setString("type", str);
					}
					else if(output.getItem() instanceof ItemGate)
					{
						ItemGate item = (ItemGate)output.getItem();
						Set<IGateExpansion> expansionSet = Invoke(getInstalledExpansions, null, output);
						NBTTagList tagList = new NBTTagList();
						for(IGateExpansion exp : expansionSet)
						{
							NBTTagString expNBT = new NBTTagString(exp.getDisplayName());
							tagList.appendTag(expNBT);
						}
						nbt.setTag("expansions", tagList);
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
