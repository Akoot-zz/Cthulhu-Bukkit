package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

public class CommandClear extends Command
{
	public CommandClear()
	{
		this.color = "&6";
		this.name = "clear";
		this.permission = "";
	}

	@Override
	public void onCommand()
	{
		if(args.length == 0)
		{
			if(sender instanceof Player)
			{
				Player player = (Player) sender;
				player.getInventory().clear();
				sendMessage("Inventory cleared! Type /clear &cundo " + color + " to undo.");
			}
		}
		if(args.length == 1)
		{
			if(sender instanceof Player)
			{
			}
		}
	}
}
