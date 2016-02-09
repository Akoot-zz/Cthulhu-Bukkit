package com.Akoot.cthulhu.commands;

public class CommandCthulhu extends Command
{
	public CommandCthulhu()
	{
		this.color = "&a";
		this.name = "cthulhu";
		this.permission = "";
	}
	
	@Override
	public void onCommand()
	{
		sendMessage("Plugin commands&f:");
		for(Command cmd: plugin.commands.commands)
		{
			sendMessage(cmd.color + cmd.name + ": &f" + cmd.permission);
		}
	}
}
