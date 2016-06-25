package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

public class CommandLogin extends Command
{
	public CommandLogin()
	{
		this.name = "login";
		this.permission = "";
		this.color = "&8";
		this.playerOnly = true;
	}

	@Override
	public void onCommand()
	{
		Player player = (Player) sender;
		if(player.isOp() && !plugin.getPlayerDataFile(player).getBoolean("logged-in"))
		{
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase(plugin.config.getString("password")))
				{
					plugin.getPlayerDataFile(player).set("logged-in", true);
					sendMessage("HI");
					player.setWalkSpeed(0.2F);
					player.setFlySpeed(0.1F);
					return;
				}
				else
				{
					for(Player p: plugin.getServer().getOnlinePlayers())
					{
						if(p != player && p.isOp()) p.sendMessage(player.getName() + " tried to login, but failed lol");
					}
					sendMessage("&4You fool...");
					return;
				}
			}
			sendUsage("/login <password>", "/login ");
		}
	}
}
