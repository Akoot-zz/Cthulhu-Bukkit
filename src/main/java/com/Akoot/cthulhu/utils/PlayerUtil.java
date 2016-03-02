package com.Akoot.cthulhu.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

public class PlayerUtil
{
	public static Player getPlayer(String search)
	{
		for(Player p: Bukkit.getOnlinePlayers())
		{
			if(compare(search, p)) return p;
		}
		return null;
	}

	public static Boolean compare(String search, Player player)
	{
		search = search.toLowerCase();
		String name = player.getName().toLowerCase();
		String displayname = ChatColor.stripColor(player.getDisplayName()).toLowerCase();
		if(name.contains(search) || displayname.contains(search)) return true;
		return false;
	}

	public static Player getPlayer(String search, Boolean tru)
	{
		Player player = getPlayer(search);
		if(player == null)
		{
			if(Bukkit.getPluginManager().getPlugin("Essentials") != null)
			{
				Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
				for(OfflinePlayer p: Bukkit.getServer().getOfflinePlayers())
				{
					if(ess.getOfflineUser(p.getName()) != null)
					{
						User user = ess.getOfflineUser(p.getName());
						if(compare(search, user.getBase())) return user.getBase();
					}
				}
			}
		}
		return player;
	}
}
