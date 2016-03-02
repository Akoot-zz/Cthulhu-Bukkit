package com.Akoot.cthulhu.commands;

import org.bukkit.command.CommandSender;

import com.Akoot.cthulhu.CthUser;
import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;

import mkremins.fanciful.FancyMessage;

public class Command
{
	public Cthulhu plugin;
	public String name;
	public String color = "&f";
	public String permission;
	public CommandSender sender;
	public boolean playerOnly = false;
	public FancyMessage message;
	public CthUser user;

	public String[] args;

	public Command(){}
	public void onCommand(){}

	public void sendUsage()
	{
		message = new FancyMessage(ChatUtil.color(color + "Usage: "));
		message.then("/" + name)
		.suggest("/" + name)
		.tooltip(ChatUtil.color(color + "Suggest: /" + name))
		.send(sender);
	}
	
	public void noPermission(String use)
	{
		sendMessage("&4You do not have permission to " + use);
	}
	
	public void sendError(String error)
	{
		sendMessage("&4Error: &c");
	}
	
	public void noPermission()
	{
		noPermission("use: &c/" + name);
	}
	
	public void sendPlayerNull(String arg)
	{
		sendMessage("Can't find player: &7&o" + arg);
	}

	public void sendMessage(String msg)
	{
		if(sender != null)
		{
			sender.sendMessage(ChatUtil.color(color + msg));
		}
	}
	
	public void sendMessage(String msg, String hover)
	{
		message = new FancyMessage(ChatUtil.color(msg));
		message.tooltip(ChatUtil.color(hover))
		.send(sender);
	}
	
	public void sendUsage(String msg)
	{
		sendUsage(msg, msg);
	}
	
	public void suggest(String msg, String suggestion)
	{
		message = new FancyMessage(ChatUtil.color(color + msg));
		message.suggest(suggestion)
		.send(sender);
	}
	
	public void sendCommand(String msg, String command)
	{
		message = new FancyMessage(ChatUtil.color(color + msg));
		message.command(command)
		.send(sender);
	}
	
	public void sendUsage(String msg, String suggest)
	{
		message = new FancyMessage(ChatUtil.color(color + "Usage: &f"));
		message.then(ChatUtil.color(msg))
		.tooltip(ChatUtil.color(color + "Suggest: " + msg))
		.suggest(suggest)
		.send(sender);
	}
	
	public void sendMessage(String msg, boolean stripColor)
	{
		if(sender != null)
		{
			sender.sendMessage(msg);
		}
	}
}
