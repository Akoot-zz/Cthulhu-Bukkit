package com.Akoot.cthulhu.commands;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandSay extends Command
{
	public CommandSay()
	{
		this.color = "&3";
		this.name = "say";
		this.permission = "bukkit.command.say";
	}

	@Override
	public void onCommand()
	{
		if(args.length > 1)
		{
			String msg = ChatUtil.color(ChatUtil.toString(args));
			sender.getServer().broadcastMessage(ChatUtil.color("&f[&dServer&f] " + msg));
		}
		else
		{
			sendMessage("Usage: &f/say <message>");
		}
	}
}
