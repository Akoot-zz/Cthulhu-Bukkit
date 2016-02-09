package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

public class CommandMake extends Command
{
	public CommandMake()
	{
		this.color = "&a";
		this.name = "make";
		this.permission = "cthulhu.make";
	}
	
	public void sendUsage()
	{
		sendMessage("Usage: &f/make <&dplayer&f> <&csay&f|&6do&f> &f<&cmessage&f|&6command&f>");
	}
	
	@Override
	public void onCommand()
	{		
		if(args.length == 0)
		{
			sendUsage();
		}
		else if(args.length >= 3)
		{
			Player target = plugin.getPlayer(args[0]);
			if(target != null)
			{
				String msg = "";
				for(int i = 2; i < args.length; i++)
				{
					msg += args[i] + " ";
				}
				msg = msg.trim();
				
				if(args[1].equalsIgnoreCase("say"))
				{
					target.chat(msg);
				}
				else if(args[1].equalsIgnoreCase("do"))
				{
					sendMessage("Forcing &6" + target.getName() + this.color + " to execute: &f/" + msg);
					target.chat("/" + msg);
				}
				else
				{
					sendMessage("&cInvalid subcommand: &6" + args[1]);
				}
			}
			else
			{
				sendMessage("&cPlayer not found: &6" + args[0]);
			}
		}
		else
		{
			sendUsage();
		}
	}
}
