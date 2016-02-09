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

	public String[] args;

	public Command(){}
	public void onCommand(){}

	public void sendUsage()
	{
		sendMessage(this.color + "Usage: &f/" + name);
	}

	public void sendMessage(String msg)
	{
		if(sender != null)
		{
			sender.sendMessage(ChatUtil.color(this.color + msg));
		}
	}
}
