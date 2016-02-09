package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandNick extends Command
{
	public CommandNick()
	{
		this.color = "&b";
		this.name = "nick";
		this.permission = "cthulhu.nick";
	}
	
	public void sendUsage()
	{
		sendMessage("Usage: &f/nick &f[player] <name>");
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(args.length == 0)
			{
				sendUsage();
			}
			else if(args.length >= 1)
			{
				@SuppressWarnings("deprecation")
				Player target = player.getServer().getPlayer(args[0]);
				String nick = "";
				if(target != null)
				{
					for(String s: args)
					{
						if(!s.equals(args[0]))
						{
							nick += s + " ";
						}
					} 
					nick = ChatUtil.color(nick.trim());
				}
				else
				{
					target = player;
					nick = ChatUtil.color(ChatUtil.toString(args)).trim();
				}
				sendMessage("Set &f" + (target == player ? "your" : target.getName() + "'s") + this.color + " nickname to: &7" + nick);
				plugin.getPlayerDataFile(target).set("displayname", nick);
			}
			else
			{
				sendUsage();
			}
		}
	}
}
