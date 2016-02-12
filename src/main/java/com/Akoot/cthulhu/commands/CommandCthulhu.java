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
		for(Command cmd: plugin.commands.commands)
		{
			sendMessage("&7[" + cmd.color + cmd.name + "&7]");
			cmd.sender = sender;
			sendMessage(cmd.color + "Description: &f" + plugin.getCommand(cmd.name).getDescription());
			cmd.sendUsage();
		}
	}
}
