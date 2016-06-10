package pome.waila.bcaddon.util;

import static pome.waila.bcaddon.reflection.ReflectedObjects.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import buildcraft.api.boards.RedstoneBoardNBT;
import buildcraft.api.boards.RedstoneBoardRegistry;
import buildcraft.robotics.ItemRedstoneBoard;
import buildcraft.robotics.ItemRobot;
import buildcraft.silicon.TileLaserTableBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Utils
{

	public static double predictRemTime(TileLaserTableBase table,TimeHolder timeHolder)
	{
		try
		{
			int energyCost = Invoke(getRequiredEnergy, table);//table.getRequiredEnergy();
			int currentEnergy = Invoke(getEnergy, table);//table.getEnergy();
			long lastMillisec = 0;
			int lastEnergy = 0;

			if(timeHolder == null)
			{
				timeHolder = new TimeHolder(System.currentTimeMillis(),currentEnergy);
				lastMillisec = timeHolder.getLastMillisec();
				lastEnergy = timeHolder.getLastEnergy();
			}
			else
			{
				lastMillisec = timeHolder.getLastMillisec();
				lastEnergy = timeHolder.getLastEnergy();
			}
			double deltaTime = (double)(System.currentTimeMillis() - lastMillisec) / 1000;
			int deltaFlow = currentEnergy - lastEnergy;
			if(deltaFlow < 0)
			{
				deltaFlow = 0;
			}
			int toFlow = energyCost - currentEnergy;

			timeHolder.setValue(System.currentTimeMillis(),currentEnergy);
			if(deltaTime == 0 || deltaFlow == 0)
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
	public static void removeTag(NBTTagCompound nbt,String tag)
	{
		if(nbt.hasKey(tag))
		{
			nbt.removeTag(tag);
		}
	}
	public static String getRedstoneBoardName(ItemStack stack)
	{
		if(stack == null || !stack.hasTagCompound() || !(stack.getItem() instanceof ItemRobot || stack.getItem() instanceof ItemRedstoneBoard))
		{
			return "UNKNOWN";
		}
		RedstoneBoardNBT rbNBT = RedstoneBoardRegistry.instance.getRedstoneBoard(stack.stackTagCompound);
		if(rbNBT == null)
		{
			return "UNKNOWN";
		}
		if(rbNBT.getID() == RedstoneBoardRegistry.instance.getEmptyRobotBoard().getID())
		{
			return "empty";
		}
		if(stack.getItem() instanceof ItemRobot)
		{
			return getBCBNBT(stack,ItemRobot.getRobotNBT(stack));
		}
		return getBCBNBT(stack, rbNBT);
	}
	public static String getBCBNBT(ItemStack stack,RedstoneBoardNBT bcbn)
	{
		List<String> list = new ArrayList<String>();
		bcbn.addInformation(stack, null, list, false);
		return list.get(0);
	}
	public static <T> List<T> copyList(List<T> source,int min)
	{
		List<T> list = new ArrayList<T>();
		for(int i = min;i < source.size();i++)
		{
			list.add(source.get(i));
		}
		return list;
	}
	public static void writeStackToNBT(NBTTagCompound nbt,String tag,ItemStack stack)
	{
		NBTTagCompound subNBT = new NBTTagCompound();
		stack.writeToNBT(subNBT);
		nbt.setTag(tag, subNBT);
	}
	public static String formatTime(double seconds)
	{
		if(Double.isInfinite(seconds) || Double.isNaN(seconds))
		{
			return "\u221es";
		}
		String ret = "";
		if(seconds >= 3600)
		{
			int hour = ((int)seconds) / 3600;
			ret += hour + "h";
			seconds -= 3600 * hour;
		}
		if(seconds >= 60)
		{
			int minute = ((int)seconds) / 60;
			ret += minute + "m";
			seconds -= 60 * minute;
		}
		BigDecimal bd = new BigDecimal(seconds);
		ret += bd.setScale(1, RoundingMode.DOWN).toString() + "s";
		return ret;
	}
	public static String formatString(ItemStack is)
	{
		if(is == null)
		{
			return "NULL";
		}
		String dispName = is.getDisplayName();
		if(is.stackSize > 1)
		{
			dispName += " x" + is.stackSize;
		}
		return dispName;
	}
}
