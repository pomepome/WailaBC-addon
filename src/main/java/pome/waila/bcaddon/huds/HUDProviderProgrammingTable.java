package pome.waila.bcaddon.huds;

import static pome.waila.bcaddon.WailaAddonBC.*;
import static pome.waila.bcaddon.modules.BCProgrammingTableModule.*;

import java.util.List;

import buildcraft.api.boards.RedstoneBoardNBT;
import buildcraft.api.boards.RedstoneBoardRegistry;
import buildcraft.api.recipes.IProgrammingRecipe;
import buildcraft.core.lib.utils.StringUtils;
import buildcraft.robotics.ItemRedstoneBoard;
import buildcraft.robotics.boards.BCBoardNBT;
import buildcraft.silicon.TileProgrammingTable;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import pome.waila.bcaddon.WailaAddonBC;

public class HUDProviderProgrammingTable implements IWailaDataProvider
{
	long lastMillisec;
	int lastEnergy;

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
				defaulttip.add(EnumChatFormatting.BLUE+ "Output: " + EnumChatFormatting.RESET + tag.getString("content"));
			}
			else
			{
				defaulttip.add(EnumChatFormatting.BLUE+ "Output: " + EnumChatFormatting.RESET + "NULL");
			}
			if(tag.hasKey("type"))
			{
				defaulttip.add(EnumChatFormatting.BLUE+ "Type: " + EnumChatFormatting.RESET + EnumChatFormatting.BOLD + tag.getString("type"));
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
		if(tile instanceof TileProgrammingTable)
		{
			TileProgrammingTable table = (TileProgrammingTable)tile;

			double estTime = predictRemTime(table);
			nbt.setDouble("estTime", estTime);

			boolean canCrafting = Invoke(canCraft,table);
			nbt.setBoolean("canCraft", canCrafting);

			removeTag(nbt,"type");
			removeTag(nbt, "content");

			IProgrammingRecipe current = getFieldValue(currentRecipe, table);
			if(current != null)
			{
				int optionIndex = getFieldValue(optionId, table);

				if(optionIndex > 0)
				{

					List<ItemStack> ops = getFieldValue(options,table);

					ItemStack first = Invoke(getStackInSlot,table,0);
					ItemStack second = ops.get(optionIndex);
					ItemStack output = current.craft(first, second);

					nbt.setString("content", output.getDisplayName());

					if(output != null && output.getItem() instanceof ItemRedstoneBoard)
					{
						RedstoneBoardRegistry reg = getFieldValue(INSTANCE, null);
						RedstoneBoardNBT<?> rbNBT = reg.getRedstoneBoard(output.stackTagCompound);

						WailaAddonBC.log.info(rbNBT.getID());

						if(rbNBT instanceof BCBoardNBT)
						{
							BCBoardNBT bcb = (BCBoardNBT)rbNBT;
							String upper_name = getFieldValue(upperName, bcb);

							String localized = StringUtils.localize(new StringBuilder().append("buildcraft.boardRobot").append(upper_name).toString());

							nbt.setString("type", localized);
						}
					}
				}
			}
		}
		return nbt;
	}
	public double predictRemTime(TileProgrammingTable table)
	{
		try
		{
		int energyCost = Invoke(getRequiredEnergy, table);//table.getRequiredEnergy();
		int currentEnergy = Invoke(getEnergy, table);//table.getEnergy();

		double deltaTime = (double)(System.currentTimeMillis() - lastMillisec) / 1000;
		int deltaFlow = currentEnergy - lastEnergy;
		if(deltaFlow < 0)
		{
			deltaFlow = 0;
		}
		int toFlow = energyCost - currentEnergy;

		lastEnergy = currentEnergy;
		lastMillisec = System.currentTimeMillis();
		if(deltaTime == 0)
		{
			return 0;
		}
		return (double)toFlow / (deltaFlow / deltaTime);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
}
