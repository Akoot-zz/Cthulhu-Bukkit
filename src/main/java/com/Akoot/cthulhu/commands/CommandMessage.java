package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandMessage extends Command
{
	public CommandMessage()
	{
		this.color = "&a";
		this.name = "message";
		this.permission = "";
	}

	@Override
	public void onCommand()
	{
		if(args.length >= 2)
		{
			Player player = plugin.getPlayer(args[0]);
			if(player != null)
			{
				String msg = ChatUtil.color(ChatUtil.toString(args));
				String name = (sender instanceof Player ? ((Player)sender).getName() : "Server");
				player.sendMessage("[" + name + "] &f" + msg);
			}
			else
			{
				sendPlayerNull(args[0]);
			}
		}
	}
}
