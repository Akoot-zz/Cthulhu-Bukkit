package com.Akoot.cthulhu;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.Akoot.cthulhu.commands.Commands;
import com.Akoot.cthulhu.events.PlayerEvents;
import com.Akoot.cthulhu.events.ServerEvents;
import com.Akoot.cthulhu.utils.CthFile;
import com.Akoot.cthulhu.utils.TaskScheduler;
import com.earth2me.essentials.Essentials;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class Cthulhu extends JavaPlugin
{
	public static String DBOX_ACCESS_TOKEN;

	public PlayerEvents playerEvents;
	public Commands commands;
	public ServerEvents serverEvents;
	public CthFile config;
	public File dataFolder;
	public File playerFolder;
	public File logFolder;
	public TaskScheduler scheduler;
	public CthFile chatLog;
	public CthFile commandLog;

	public Logger log;

	@Override
	public void onEnable()
	{
		log = getLogger();
		playerEvents = new PlayerEvents(this);
		serverEvents = new ServerEvents(this);
		commands = new Commands(this);
		scheduler = new TaskScheduler(this);
		
		scheduler.schedule(playerEvents, "updatePlaytime", 60);

		dataFolder = getDataFolder();
		playerFolder = new File(getDataFolder(), "userdata");
		logFolder = new File(getDataFolder(), "logs");
		config = new CthFile(getDataFolder(), "config");
		
		chatLog = new CthFile(logFolder, "chat-log");
		commandLog = new CthFile(logFolder, "commands-log");
		
		createDirectories();

	}

	public Permission getPermissions()
	{
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		Permission perms = rsp.getProvider();
		return perms;
	}

	public Essentials getEssentials()
	{
		Essentials essentials = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
		return essentials;
	}

	public Chat getChat()
	{
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
		Chat chat = chatProvider.getProvider();
		return chat;
	}

	public boolean hasPlugin(String pluginName)
	{
		return getServer().getPluginManager().getPlugin(pluginName) != null;
	}

	public boolean hasPlayerData(Player player)
	{
		CthFile playerFile = new CthFile(playerFolder, player.getUniqueId().toString());
		return playerFile.exists();
	}

	public CthFile getPlayerDataFile(Player player)
	{
		CthFile playerFile = new CthFile(playerFolder, player.getUniqueId().toString());
		return playerFile;
	}

	public void createDirectories()
	{
		if(!dataFolder.exists()) dataFolder.mkdir();

		if(!playerFolder.exists()) playerFolder.mkdir();
		
		if(!logFolder.exists()) logFolder.mkdir();
		
		if(!chatLog.exists()) chatLog.create();
		
		if(!commandLog.exists()) commandLog.create();

		if(!config.exists())
		{
			config.create();
			config.addComment("Welcome to the config!");
			config.addComment("The prefix you wish to use for (most) plugin messages");
			config.set("prefix", "&7[&aServer&7]");
			config.addComment("Default chat format for players who do not have a custom chat format.");
			config.set("default-chat-format", "<{displayname}> {message}");
			config.addComment("MOTD shown before joining the server (server browser)");
			config.set("motd", "&aA Minecraft Server");
			config.set("dropbox-key", "", "In development! Please ignore");
			config.set("check-server-peek", false);
			config.set("xp-orb", "xp-orb");
		}
	}

	@SuppressWarnings("deprecation")
	public Player getPlayer(String find)
	{
		Player player = getServer().getPlayer(find);
		if(player == null)
		{
			for(Player p: getServer().getOnlinePlayers())
			{
				if(p.getUniqueId().equals(find) || p.getName().startsWith(find) || p.getName().equalsIgnoreCase(find) || p.getDisplayName().startsWith(find) || p.getDisplayName().equalsIgnoreCase(find))
				{
					player = p;
				}
			}
		}
		return player;
	}

	public Player getPlayer(String find, boolean offline)
	{
		Player player = getPlayer(find);
		if(getEssentials() != null)
		{
			if(player == null)
			{
				if(getEssentials().getOfflineUser(find) != null)
				{
					player = getEssentials().getOfflineUser(find).getBase();
				}
				else
				{
					for(OfflinePlayer op: getServer().getOfflinePlayers())
					{
						if(getEssentials().getOfflineUser(op.getName()) != null)
						{
							Player p = getEssentials().getOfflineUser(op.getName()).getBase();
							if(p.getUniqueId().equals(find) || p.getName().startsWith(find) || p.getName().equalsIgnoreCase(find) || p.getDisplayName().startsWith(find) || p.getDisplayName().equalsIgnoreCase(find))
							{
								player = p;
							}
						}
					}
				}
			}
		}
		return player;
	}

	public String getPrefix()
	{
		return config.getString("prefix");
	}

	@Override
	public void onDisable()
	{
	}
}
