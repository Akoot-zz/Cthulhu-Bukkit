package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

public class CommandRedeem extends Command
{
	public CommandRedeem()
	{
		this.color = "&6";
		this.name = "redeem";
		this.permission = "";
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(args.length == 0)
			{
				sendUsage("/redeem -<type> <prize>c");
			}
			else if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("-g"))
				{
					if(plugin.getPermissions() != null)
					{
						sendUsage("/redeem -g <group>");
					}
					else
					{
						sendMessage("-g is not a valid tag because it lacks a permissions plugin");
					}
				}
			}
			else if(args.length == 2)
			{
				if(args[0].equalsIgnoreCase("-g"))
				{
					if(plugin.getPermissions() != null)
					{
						for(String group: plugin.getPermissions().getGroups())
						{
							if(args[1].equalsIgnoreCase(group))
							{
								if(player.hasPermission("redeem.group." + group))
								{
									plugin.getPermissions().playerAddGroup(player, group);
									sendMessage("Successfully redeemed " + plugin.getChat().getGroupPrefix(player.getWorld(), group));
									return;
								}
								else
								{
									noPermission("redeem &6" + group);
									return;
								}
							}
							sendError("Group not found: &7" + args[2]);
						}
						sendMessage("&cError: &fGroup not found: &7" + args[2]);
					}
					else
					{
						sendMessage("-g is not a valid tag because it lacks a permissions plugin");
					}
				}
			}
		}
	}
}
