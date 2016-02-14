package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;


public class CommandChatFormat extends Command
{
	public CommandChatFormat()
	{
		this.color = "&a";
		this.name = "chat-format";
		this.permission = "cthulhu.chat-format";
	}

//	public void sendUsage()
//	{
//		sendMessage("Usage:");
//		sendMessage("&f/cf &7&oShows what your current chat format looks like");
//		sendMessage("&f/cf <player> &7&oShows what a player's chat format looks like");
//		sendMessage("&f/cf <format> &7&oChanges your chat format");
//		sendMessage("&f/cf -p:<player> <format> &7&oSpecify a player with -p:<player> and change their chat format");
//		sendMessage("Type &f/cf help " + this.color + "for help with variables");
//	}

	private void sendFormat(Player player)
	{
		sendMessage("&f" + (player == (Player)sender ? "your" : player.getName() + "'s") + this.color + " chat format:");
		if(plugin.getPlayerDataFile(player).has("chat-format"))
		{
			sendMessage(plugin.getPlayerDataFile(player).getString("chat-format"), true);
		}
		else
		{
			sendMessage(plugin.config.getString("default-chat-format"), true);
		}
	}

	private void sendVars()
	{
		sendMessage("&7Username: &f{username}, {uname}, {name}, {n}");
		sendMessage("&7Displayname: &f{displayname}, {dname}, {nickname}, {nick}");
		sendMessage("&7Message: &f{message}, {msg}, {m}, {txt}");
		sendMessage("&7Title: &f{title}, {prefix} &7&oThis is not the group prefix");
		sendMessage("&2Faction: &f{faction}, {fac}, {f}");
		sendMessage("&2Faction name: &f{factions_name\\|rp}");
		sendMessage("&2Faction role prefix: &f{factions_roleprefix}");
		sendMessage("&2Faction relation color: &f{factions_relcolor}");
		sendMessage("&6Group: &f{group}, {g}");
		sendMessage("&6Group prefix: &f{group-prefix}, {p}");
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(args.length == 0)
			{
				sendFormat(player);
			}
			else if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("reset"))
				{
					plugin.getPlayerDataFile(player).set("chat-format", plugin.config.getString("default-chat-format"));
					sendMessage("&7Your chat format was reset");
				}
				else if(args[0].matches("(\\?|h|help|vars|v)"))
				{
					sendVars();
				}
				else
				{
					Player target = plugin.getPlayer(args[0], true);
					if(target != null)
					{
						sendFormat(target);
					}
					else
					{
						sendPlayerNull(args[0]);
					}
				}
			}
			else if(args.length >= 2)
			{
				Player target = plugin.getPlayer(args[0].substring(3), true);
				int index = 1;
				if(target == null)
				{
					target = player;
					index = 0;
				}
				String fmt = "";
				for(int i = index; i < args.length; i++)
				{
					fmt += args[i] + " ";
				}
				fmt = fmt.trim();
				plugin.getPlayerDataFile(target).set("chat-format", fmt);
				sendFormat(target);
			}
			else
			{
				sendUsage();
			}
		}
		else
		{
			if(args.length == 0)
			{
				sendUsage();
			}
			else if(args.length >= 2)
			{
				Player target = plugin.getPlayer(args[0].substring(3), true);
				if(target != null)
				{
					String fmt = "";
					for(int i = 1; i < args.length; i++)
					{
						fmt += args[i] + " ";
					}
					fmt = fmt.trim();
					plugin.getPlayerDataFile(target).set("chat-format", fmt);
					sendFormat(target);
				}
				else
				{
					sendPlayerNull(args[0]);
				}
			}
			else
			{
				sendUsage();
			}
		}
	}
}
