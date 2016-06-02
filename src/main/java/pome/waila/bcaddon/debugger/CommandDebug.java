package pome.waila.bcaddon.debugger;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import pome.waila.bcaddon.WailaAddonBC;

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
				sendChat(player,WailaAddonBC.getRedstoneBoardName(stack));
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
