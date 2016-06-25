package com.Akoot.cthulhu.commands;

public class CommandCthulhu extends Command
{
	public CommandCthulhu()
	{
		this.color = "&a";
		this.name = "cthulhu";
		this.permission = "cthulhu";
	}

	@Override
	public void onCommand()
	{
		if(args.length == 0)
		{
			for(Command cmd: plugin.commands.commands)
			{
				sendMessage("&7[" + cmd.color + cmd.name + "&7]");
				cmd.sender = sender;
				sendMessage(cmd.color + "Description: &f" + plugin.getCommand(cmd.name).getDescription());
				cmd.sendUsage();
				sendMessage("");
			}
		}
		else if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase("rebuild") || args[0].equalsIgnoreCase("reload"))
			{
				if(plugin.hasPlugin("PluginManager") || plugin.hasPlugin("PluginManagerReloaded"))
				{
					plugin.getServer().dispatchCommand(sender, "plm reload Cthulhu");
				}
				else
				{
					sendMessage("&4Plugin manager is not installed");
				}
			}
		}
	}
}
