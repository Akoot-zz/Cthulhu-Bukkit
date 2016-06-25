package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

public class CommandTeleport extends Command
{
	public CommandTeleport()
	{
		this.name = "tp";
		this.color = "&7";
		this.permission = "cthulhu.teleport";
	}
	
	@Override
	public void onCommand()
	{
		if(args.length == 1 && sender instanceof Player)
		{
			Player player = (Player)sender;
			Player target = plugin.getPlayer(args[0]);
			if(target != null) player.teleport(target.getLocation());
		}
		if(args.length == 2)
		{
			Player p1 = plugin.getPlayer(args[0]);
			Player p2 = (sender instanceof Player &&(args[1].equalsIgnoreCase("me")) ? (Player) sender : plugin.getPlayer(args[1]));
			if(p1 != null && p2 != null)
			{
				p1.teleport(p2.getLocation());
			}
			if(p1 == null) sendPlayerNull(args[0]);
			if(p2 == null) sendPlayerNull(args[1]);
		}
	}
}
