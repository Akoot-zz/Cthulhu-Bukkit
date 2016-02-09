package com.Akoot.cthulhu.commands;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandRaw extends Command
{
	public CommandRaw()
	{
		this.color = "&9";
		this.name = "raw";
		this.permission = "cthulhu.raw";
	}

	@Override
	public void onCommand()
	{
		if(args.length > 0)
		{
			String msg = ChatUtil.toString(args);
			sender.getServer().broadcastMessage(ChatUtil.color(msg));
		}
		else
		{
			sendMessage("Usage: &f/raw <message>");
		}
	}
}
