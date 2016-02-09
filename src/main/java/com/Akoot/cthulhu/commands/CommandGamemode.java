package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

public class CommandGamemode extends Command
{
	public CommandGamemode()
	{
		this.name = "cthulhu";
		this.permission = "";
	}
	
	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			player.getEyeLocation().setPitch(player.getEyeLocation().getPitch() + 1);
		}
	}
}
