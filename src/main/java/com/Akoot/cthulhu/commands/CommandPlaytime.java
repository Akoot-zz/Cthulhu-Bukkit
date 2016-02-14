package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

import com.Akoot.cthulhu.utils.ChatUtil;

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

	public void sendPlaytime(Player player)
	{
		int time = plugin.getPlayerDataFile(player).getInt("playtime");

		sendMessage("Playtime: &f" + ChatUtil.getTime(time));
	}
}
