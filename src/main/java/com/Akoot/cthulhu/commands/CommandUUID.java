package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class CommandUUID extends Command
{
	public CommandUUID()
	{
		this.color = "&a";
		this.name = "uuid";
		this.permission = "cthulhu.uuid";
	}
	
	public void sendUsage()
	{
		sendMessage("Usage: &f/uuid [player|players...]");
		sendMessage("Example: &f/uuid notch &7&oDisplay a player's UUID");
		sendMessage("Example: &f/uuid notch jeb_ dinnerbone &7&oDisplay multiple players' UUID's");
	}
	
	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(args.length == 0)
			{
				sendMessage("Your UUID: &f" + player.getUniqueId());
			}
			else if(args.length >= 1)
			{
				for(String a: args)
				{
					Player target = player.getServer().getPlayer(a);
					if(target != null)
					{
						sendMessage(target.getName() + ": &f" + target.getUniqueId().toString());
					}
					else
					{
						sendMessage("&cPlayer not found: &6" + a);
					}
				}
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
			else if(args.length >= 1)
			{
				for(String a: args)
				{
					Player target = sender.getServer().getPlayer(a);
					if(target != null)
					{
						sendMessage(target.getName() + ": &f" + target.getUniqueId().toString());
					}
					else
					{
						sendMessage("&cPlayer not found: &6" + a);
					}
				}
			}
			else
			{
				sendUsage();
			}
		}
	}
}
