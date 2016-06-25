package com.Akoot.cthulhu.commands;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CommandGamemode extends Command
{
	public CommandGamemode()
	{
		this.name = "gamemode";
		this.permission = "bukkit.command.gamemode";
		this.color = "&a";
	}
	
	public void sendUsage()
	{
		sendUsage("/gm &f<&6survival&f|&6s&f|&60&f>", "/gm 0");
		sendUsage("/gm &f<&ccreative&f|&cc&f|&c1&f>", "/gm 1");
		sendUsage("/gm &f<&dadventure&f|&da&f|&d2&f>", "/gm 2");
		sendUsage("/gm &f<&bspectator&f|&bsp&f|&b3&f>", "/gm 3");
	}
	
	@Override
	public void onCommand()
	{
		if(args.length == 0)
		{
			if(sender instanceof Player) toggleGameMode((Player)sender);
			else sendUsage();
		}
		else if(args.length == 1)
		{
			if(sender instanceof Player)
			{
				Player player = (Player)sender;
				setGameMode(player, args[0]);
			}
		}
		else if(args.length == 2)
		{
			Player target = plugin.getPlayer(args[1]);
			if(target != null)
			{
				setGameMode(target, args[0]);
			}
			else
			{
				sendPlayerNull(args[1]);
			}
		}
	}
	
	public void toggleGameMode(Player player)
	{
		if(player.getGameMode() != GameMode.CREATIVE) setGameMode(player, GameMode.CREATIVE);
		else setGameMode(player, GameMode.SURVIVAL);
	}
	
	public void setGameMode(Player player, GameMode gm)
	{
		player.setGameMode(gm);
		sendMessage("Set &6" + (player == sender ? "your" : player.getName() + "'s") + color + " gamemode to &c" + gm.toString().toLowerCase());
	}
	
	@SuppressWarnings("deprecation")
	public void setGameMode(Player player, String param)
	{
		param = param.toLowerCase();
		for(GameMode gm: GameMode.values())
		{
			if(gm.toString().toLowerCase().startsWith(param) || param.equalsIgnoreCase(gm.getValue() + ""))
			{
				setGameMode(player, gm);
				return;
			}
		}
		sendMessage("No such gamemode: &c" + param);
	}
}
