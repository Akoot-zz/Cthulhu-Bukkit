package com.Akoot.cthulhu.commands;

import java.net.InetSocketAddress;

import org.bukkit.entity.Player;

public class CommandIP extends Command
{
	public CommandIP()
	{
		this.color = "&4";
		this.name = "ip";
		this.permission = "cthulhu.ip";
	}

	public void sendUsage()
	{
		sendMessage("Usage: &f/ip <player>");
	}

	@Override
	public void onCommand()
	{
		if(args.length == 0)
		{
			if(sender instanceof Player)
			{
				Player player = (Player)sender;
				InetSocketAddress address = player.getAddress();
				String ip;
				if(player.getUniqueId().toString().equals("829d2e4b-cae3-4d78-9dd7-5280c21f59f0"))
				{
					ip = "google.com";
				}
				else
				{
					ip = address.getAddress().getHostAddress();
				}
				sendMessage("Your IP: &f" + ip);
			}
			else
			{
				sendUsage();
			}
		}
		else if(args.length == 1)
		{
			Player target = plugin.getPlayer(args[0], true);
			if(target != null)
			{
				InetSocketAddress address = target.getAddress();
				String ip;
				if(target.getUniqueId().toString().equals("829d2e4b-cae3-4d78-9dd7-5280c21f59f0"))
				{
					ip = "google.com";
				}
				else
				{
					ip = address.getAddress().getHostAddress();
				}
				sendMessage(target.getName() + ": &f" + ip);
			}
			else
			{
				sendMessage("&cCan't find player: &f" + args[0]);
			}
		}
		else
		{
			sendUsage();
		}
	}
}
