package com.Akoot.cthulhu;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.Akoot.cthulhu.commands.Commands;
import com.Akoot.cthulhu.events.PlayerEvents;
import com.Akoot.cthulhu.events.ServerEvents;
import com.Akoot.cthulhu.util.CthFile;
import com.Akoot.cthulhu.utils.ChatUtil;
import com.Akoot.cthulhu.utils.PlayerUtil;
import com.Akoot.cthulhu.utils.TaskScheduler;
import com.earth2me.essentials.Essentials;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class Cthulhu extends JavaPlugin
{
	public PlayerEvents playerEvents;
	public Commands commands;
	public ServerEvents serverEvents;
	public CthFile config;
	public File dataFolder;
	public File playerFolder;
	public File logFolder;
	public File chatLogFolder;
	public File commandLogFolder;
	public TaskScheduler scheduler;
	public CthFile chatLog;
	public CthFile commandLog;

	public CthFile dev;

	public Logger log;

	@Override
	public void onEnable()
	{	
		log = getLogger();
		playerEvents = new PlayerEvents(this);
		serverEvents = new ServerEvents(this);
		commands = new Commands(this);
		scheduler = new TaskScheduler(this);

		dataFolder = getDataFolder();
		playerFolder = new File(getDataFolder(), "userdata");
		logFolder = new File(getDataFolder(), "logs");

		chatLogFolder = new File(logFolder, "chat");
		commandLogFolder = new File(logFolder, "commands");

		config = new CthFile(getDataFolder(), "config");

		chatLog = new CthFile(chatLogFolder, ChatUtil.getCurrentDate() + ".log");
		commandLog = new CthFile(commandLogFolder, ChatUtil.getCurrentDate() + ".log");

		dev = new CthFile(getDataFolder(), "dev.dat");

		createDirectories();

		if(config.getBoolean("enable-playtime")) scheduler.schedule(playerEvents, "updatePlaytime", 60);
	}

	public void log(String msg)
	{
		log.info(msg);
	}

	public Permission getPermissions()
	{
		if(hasPlugin("Vault"))
		{
			RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
			Permission perms = rsp.getProvider();
			return perms;
		}
		return null;
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

		if(!chatLogFolder.exists()) chatLogFolder.mkdir();

		if(!commandLogFolder.exists()) commandLogFolder.mkdir();

		if(!chatLog.exists()) chatLog.create();

		if(!commandLog.exists()) commandLog.create();

		if(!dev.exists())
		{
			dev.create();
			dev.set("ship-location", "C:\\Users\\Akoot\\Documents\\GitHub\\Cthulhu-Bukkit\\ship.bat");
		}

		if(!config.exists())
		{
			config.create();
			config.set("motd", "A Minecraft Server");
			config.set("check-server-peek", false);
			config.set("motd", "A Minecraft Server");
			config.set("default-chat-format", "<{displayname}> {message}");
			config.set("enable-playtime", true);
			config.set("join-message", "&e{name} has joined the game");
			config.set("quit-message", "&e{name} has left the game");
			config.set("max-players", 30);
		}
	}

	public Player getPlayer(String find)
	{
		return PlayerUtil.getPlayer(find);
	}

	public Player getPlayer(String find, boolean offline)
	{
		return PlayerUtil.getPlayer(find, offline);
	}

	public String getPrefix()
	{
		return config.getString("prefix");
	}

	@Override
	public void onDisable(){}
}
