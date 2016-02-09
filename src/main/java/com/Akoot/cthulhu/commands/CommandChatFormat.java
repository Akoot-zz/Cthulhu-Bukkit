package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

import com.Akoot.cthulhu.utils.ChatUtil;


public class CommandChatFormat extends Command
{
	public CommandChatFormat()
	{
		this.color = "&a";
		this.name = "chat-format";
		this.permission = "cthulhu.chat-format";
	}
	
	public void sendUsage()
	{
		sendMessage("Usage: &f/cf [player] <format>");
		sendMessage("&7&oType &2/cf help &7&ofor help with variables");
	}
	
	private void sendFormat()
	{
		sendMessage("&dYour current chat format:");
		sender.sendMessage(plugin.config.getString("default-chat-format"));
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
				sendFormat();
			}
			else if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("reset"))
				{
					plugin.getPlayerDataFile(player).set("chat-format", plugin.config.get("default-chat-format"));
					sendMessage("&o&7Your chat format was reset");
				}
				else if(args[0].matches("(\\?|h|help)"))
				{
					sendVars();
				}
				else
				{
					@SuppressWarnings("deprecation")
					Player target = player.getServer().getPlayer(args[0]);
					if(target != null)
					{
						if(plugin.getPlayerDataFile(target).has("chat-format"))
						{
							sendMessage("&6" + target.getName() + "'s chat format:");
							player.sendMessage(plugin.getPlayerDataFile(target).getString("chat-format"));
						}
						else
						{
							sendMessage("&6" + target.getName() + "'s chat format:");
							player.sendMessage(plugin.getConfig().getString("default-chat-format"));
						}
					}
				}
			}
			else if(args.length >= 2)
			{
				@SuppressWarnings("deprecation")
				Player target = player.getServer().getPlayer(args[0]);
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
				target.sendMessage(ChatUtil.color("&bYour chat format was set to: &f" + fmt));
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
				@SuppressWarnings("deprecation")
				Player target = sender.getServer().getPlayer(args[0]);
				if(target != null)
				{
					String fmt = "";
					for(int i = 1; i < args.length; i++)
					{
						fmt += args[i] + " ";
					}
					fmt = fmt.trim();
					plugin.getPlayerDataFile(target).set("chat-format", fmt);
					target.sendMessage("Your chat format was set to: &f" + fmt);
				}
				else
				{
					sendMessage("&cPlayer not found: &f" + args[0]);
				}
			}
			else
			{
				sendUsage();
			}
		}
	}
}
