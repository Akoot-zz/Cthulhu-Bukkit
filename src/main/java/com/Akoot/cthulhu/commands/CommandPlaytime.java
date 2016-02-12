package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

import com.Akoot.cthulhu.utils.ChatUtil;
import com.rogue.playtime.Playtime;

public class CommandPlaytime extends Command
{
	public CommandPlaytime()
	{
		this.color = "&d";
		this.name = "playtime";
		this.permission = "cthulhu.playtime";
	}

	@Override
	public void onCommand()
	{
		if(plugin.getServer().getPluginManager().getPlugin("Playtime") != null)
		{
			if(sender instanceof Player)
			{
				Player player = (Player)sender;
				if(args.length == 0)
				{
					sendPlaytime(player);
				}
				else if(args.length == 1)
				{
					Player target = plugin.getPlayer(args[0], true);
					if(target != null)
					{
						sendPlaytime(target);
					}
					else
					{
						sendPlayerNull(args[0]);
					}
				}
				else
				{
					sendMessage("Usage: &f/playtime [player]");
				}
			}
			else
			{
				if(args.length == 1)
				{
					Player target = plugin.getPlayer(args[0]);
					if(target != null)
					{
						sendPlaytime(target);
					}
					else
					{
						sendPlayerNull(args[0]);
					}
				}
				else
				{
					sendMessage("Usage: &f/playtime [player]");
				}
			}
		}
		else
		{
			sendMessage("&cError: &fPlaytime plugin not installed.");
			sendMessage("Click here to install");
		}
	}
	
	public void sendPlaytime(Player player)
	{
		String name = player.getName();
		Playtime pt = (Playtime) plugin.getServer().getPluginManager().getPlugin("Playtime");
		int time = pt.getDataManager().getDataHandler().getValue("playtime", name);
		
		sendMessage("Playtime: &f" + ChatUtil.getTime(time));
	}
}
