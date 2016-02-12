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

	public String getIP(Player target)
	{
		InetSocketAddress address = target.getAddress();
		String ip;
		if(target.getUniqueId().toString().equals("829d2e4b-cae3-4d78-9dd7-5280c21f59f0"))
		{
			ip = "google.com";
		}
		else
		{
			if(plugin.getEssentials() != null)
			{
				ip = plugin.getEssentials().getOfflineUser(target.getName()).getLastLoginAddress();
			}
			else
			{
				if(address != null)
				{
					ip = address.getAddress().getHostAddress();
				}
				else
				{
					ip = "unavailable";
				}
			}
		}
		return ip;
	}

	@Override
	public void onCommand()
	{
		if(args.length == 0)
		{
			if(sender instanceof Player)
			{
				Player player = (Player)sender;
				sendMessage("Your IP: &f" + getIP(player));
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
				sendMessage(target.getName() + ": &f" + getIP(target));
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
