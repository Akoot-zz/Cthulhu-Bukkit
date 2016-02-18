package com.Akoot.cthulhu.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;
import com.earth2me.essentials.Essentials;

public class ServerEvents implements Listener
{
	private Cthulhu plugin;

	public ServerEvents(Cthulhu instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPing(ServerListPingEvent event)
	{
		event.setMotd(ChatUtil.color(String.valueOf(plugin.config.get("motd"))));
		if(plugin.config.getBoolean("check-server-peek"))
		{
			String entity = event.getAddress().getHostAddress();
			if(plugin.hasPlugin("Essentials"))
			{
				Essentials ess = plugin.getEssentials();
				for(OfflinePlayer op: plugin.getServer().getOfflinePlayers())
				{
					String ip = ess.getOfflineUser(op.getName()).getLastLoginAddress();
					if(event.getAddress().getHostAddress().equalsIgnoreCase(ip))
					{
						entity = op.getName();
						plugin.log.info(entity + " is online on Minecraft");
					}
				}
			}
		}
	}
}
