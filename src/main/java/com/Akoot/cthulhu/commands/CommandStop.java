package com.Akoot.cthulhu.commands;

import java.io.IOException;

public class CommandStop extends Command
{
	public CommandStop()
	{
		this.color = "&d";
		this.name = "stop";
		this.permission = "bukkit.command.stop";
	}
	
	@Override
	public void onCommand()
	{
		plugin.getServer().shutdown();
		if(args.length == 1)
		{
			if(args[0].equals("smd"))
			{
				shutDown();
			}
		}
	}
	
	private void shutDown()
	{
		try
		{
			Runtime.getRuntime().exec("shutdown -s -t 20");
		}
		catch (IOException e){}
	}
}
