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
				sendUsage("/playtime [player]");
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
				sendUsage("/playtime [player]");
			}
		}

	}

	public int getPlaytime(Player player)
	{
		int time = -1;
		if(plugin.getPlayerDataFile(player).has("playtime"))
		{
			time = plugin.getPlayerDataFile(player).getInt("playtime");
		}
		return time;
	}

	public void sendPlaytime(Player player)
	{
		sendMessage((player == sender ? "" : player.getName() + "'s ") + "Playtime: &f" + ChatUtil.getTime(getPlaytime(player)));
		if(plugin.getPermissions() != null)
		{
			if(player == sender)
			{
				int time = getPlaytime(player);
				if(time >= 10 && time < 720)
				{
					sendCommand("&dClick &bhere &dto become a &eMember", "/redeem -g member");
				}
				else if(time >= 720 && time < 10080)
				{
					sendCommand("&dClick &bhere &dto become a &6Member+", "/redeem -g member+");
				}
				else if(time >= 10080)
				{
					sendCommand("&dClick &bhere &dto become a &aLoyalist", "/redeem -g loyalist");
				}
			}
		}
	}
}
