package com.Akoot.cthulhu.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class CommandTeleportHere extends Command
{
	public CommandTeleportHere()
	{
		this.name = "tphere";
		this.color = "&7";
		this.permission = "cthulhu.teleport";
		this.playerOnly = true;
	}

	@Override
	public void onCommand()
	{
		Player player = (Player)sender;
		if(args.length > 0)
		{
			List<Player> players = new ArrayList<Player>();
			for(String s: args)
			{
				if(plugin.getPlayer(s) != null) players.add(plugin.getPlayer(s));
				else sendPlayerNull(s);
			}
			if(!players.isEmpty())
			{
				for(Player p: players) p.teleport(player.getLocation());
				return;
			}
		}
		sendUsage("/tphere <player> [players]...", "/tphere ");
	}
}
