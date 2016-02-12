package com.Akoot.cthulhu.commands;

import org.bukkit.command.CommandSender;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;

public class Command
{
	public Cthulhu plugin;
	public String name;
	public String color = "&f";
	public String permission;
	public CommandSender sender;
	public boolean playerOnly = false;
	public String arg = "";

	public String[] args;

	public Command(){}
	public void onCommand(){}

	public void sendUsage()
	{
		sendMessage(this.color + "Usage: &f/" + name);
	}
	
	public void sendPlayerNull(String arg)
	{
		sendMessage("Can't find player: &7&o" + arg);
	}

	public void sendMessage(String msg)
	{
		if(sender != null)
		{
			sender.sendMessage(ChatUtil.color(this.color + msg));
		}
	}
	
	public void sendMessage(String msg, boolean stripColor)
	{
		if(sender != null)
		{
			sender.sendMessage(msg);
		}
	}
}
