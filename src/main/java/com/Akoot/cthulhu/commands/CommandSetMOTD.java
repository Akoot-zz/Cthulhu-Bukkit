package com.Akoot.cthulhu.commands;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandSetMOTD extends Command
{
	public CommandSetMOTD()
	{
		this.color = "&e";
		this.name = "setmotd";
		this.permission = "cthulhu.motd";
	}

	@Override
	public void onCommand()
	{
		if(args.length > 0)
		{
			String msg = ChatUtil.toString(args);
			plugin.config.set("motd", ChatUtil.color(msg));
			sendMessage("Server MOTD set to: &f" + ChatUtil.color(msg));
		}
		else
		{
			sendMessage("Usage: &f/setmotd <motd>");
		}
	}
}
