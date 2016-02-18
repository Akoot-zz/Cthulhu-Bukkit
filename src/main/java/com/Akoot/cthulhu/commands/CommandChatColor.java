package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandChatColor extends Command
{
	public CommandChatColor()
	{
		this.color = "&b";
		this.name = "chat-color";
		this.permission = "cthulhu.chat-color";
	}
	
	public void sendUsage()
	{
		sendUsage("/cc <color>", "/cc " + ChatUtil.randomColor().substring(1));
		sendCommand(color + "Click here to see a list of color codes", "/cc colors");
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(args.length == 1)
			{
				if(ChatUtil.allColors().contains("&" + args[0]))
				{
					String cc = args[0];
					plugin.getPlayerDataFile(player).set("chat-color" , cc);
					sendMessage("&fChat color set to &" + args[0] + "this color");
				}
				else if(args[0].equalsIgnoreCase("colors"))
				{
					sendMessage("Color Codes");
					for(String s: ChatUtil.allColors())
					{
						sendMessage("&f" + s.substring(1) + " - " + s + "example");
					}
				}
				else
				{
					sendUsage();
				}
			}
			else
			{
				sendUsage();
			}
		}
	}
}
