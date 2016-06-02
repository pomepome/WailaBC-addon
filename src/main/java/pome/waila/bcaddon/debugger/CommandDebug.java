package pome.waila.bcaddon.debugger;

import buildcraft.api.boards.RedstoneBoardNBT;
import buildcraft.api.boards.RedstoneBoardRegistry;
import buildcraft.robotics.ItemRedstoneBoard;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class CommandDebug extends CommandBase {

	@Override
	public String getCommandName()
	{
		return "debug";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "debug";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if(sender instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)sender;
			ItemStack stack = player.getCurrentEquippedItem();
			if(stack != null)
			{
				Item item = stack.getItem();
				if(item instanceof ItemRedstoneBoard)
				{
					RedstoneBoardNBT rbNBT = RedstoneBoardRegistry.instance.getRedstoneBoard(stack.getTagCompound());

					if(rbNBT != null)
					{
						sendChat(player,rbNBT.getID());
					}
				}
			}
			else
			{
				sendChat(player,"NULL");
			}
		}
	}
	private void sendChat(EntityPlayer p,String msg)
	{
		if(p.worldObj.isRemote)
		{
			return;
		}
		p.addChatMessage(new ChatComponentText(msg));
	}
}
