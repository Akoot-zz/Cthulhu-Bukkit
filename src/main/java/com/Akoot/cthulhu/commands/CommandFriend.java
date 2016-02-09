package com.Akoot.cthulhu.commands;

import java.util.List;

import org.bukkit.entity.Player;

public class CommandFriend extends Command
{
	public CommandFriend()
	{
		this.color = "&2";
		this.name = "friend";
		this.permission = "cthulhu.friend";
	}

	public void sendUsage()
	{
		sendMessage("Usage: &7/");
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("list"))
				{
					if(plugin.getPlayerDataFile(player).getList("friends") != null)
					{
						List<Object> list = plugin.getPlayerDataFile(player).getList("friends");
						if(!list.isEmpty())
						{
							for(Object s: list)
							{
								Player friend = plugin.getPlayer(String.valueOf(s), true);
								String color = (friend.isOnline() ? "&a" : "&7");
								sendMessage(color + friend.getName());
							}
						}
						else
						{
							sendMessage("&dYou don't have any friends... :( (empty)");
							sendMessage(String.valueOf(plugin.getPlayerDataFile(player).getList("friends")));
						}
					}
					else
					{
						sendMessage("&dYou don't have any friends... :( (null)");
					}
				}
			}
			else if(args.length == 2)
			{
				Player target = plugin.getPlayer(args[1], true);
				if(target != null)
				{
					plugin.getPlayerDataFile(player).addTo("friends", target.getUniqueId().toString());
					sendMessage("&2Friend added: &f" + target.getName());
				}
				else
				{
					sendMessage("&6" + args[1] + " &chas never played on this server.");
				}
			}
		}
	}
}
