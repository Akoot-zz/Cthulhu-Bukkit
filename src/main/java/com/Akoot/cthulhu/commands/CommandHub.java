package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

public class CommandHub extends Command
{
	public CommandHub()
	{
		this.color = "&a";
		this.name = "hub";
		this.permission = "essentials.spawn";
		this.playerOnly = true;
	}
	
	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			player.chat("/spawn");
			sendMessage("&cThis is not a HUB server");
		}
	}
}
