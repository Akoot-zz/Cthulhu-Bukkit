package com.Akoot.cthulhu.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;

public class Commands implements CommandExecutor
{
	private Cthulhu plugin;

	public List<Command> commands;
	
	public Commands(Cthulhu instance)
	{
		plugin = instance;
		commands = new ArrayList<Command>();
		
		commands.add(new CommandChatFormat());
		commands.add(new CommandMake());
		//commands.add(new CommandFriend());
		commands.add(new CommandCthulhu());
		commands.add(new CommandUUID());
		commands.add(new CommandRaw());
		commands.add(new CommandSetlore());
		commands.add(new CommandRename());
		commands.add(new CommandSign());
		commands.add(new CommandSay());
		commands.add(new CommandSetMOTD());
		commands.add(new CommandPlaytime());
		commands.add(new CommandNick());
		commands.add(new CommandIP());
		commands.add(new CommandWrite());
		commands.add(new CommandChatColor());
		
		for(Command cmd: commands)
		{
			plugin.getCommand(cmd.name).setExecutor(this);
		}
	}
	
	public void addCommand(Command command)
	{
		commands.add(command);
	}

	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] args)
	{
		for(Command command: commands)
		{
			if(cmd.getName().equalsIgnoreCase(command.name))
			{
				command.plugin = plugin;
				command.sender = sender;
				command.args = args;
				
				if(sender instanceof Player)
				{
					Player player = (Player) sender;
					if(!player.hasPermission(command.permission))
					{
						command.sendMessage("&4You do not have permission to use: &f/" + command.name);
						return false;
					}
				}
				else if(sender instanceof BlockCommandSender)
				{
					if(command.playerOnly)
					{
						command.sendMessage("Sorry, /" + command.name + " can only be used by players");
						return false;
					}
					BlockCommandSender block  = (BlockCommandSender) sender;
					System.out.println(String.format("Command block at %s,%s,%s issued server command: /%s", block.getBlock().getX(), block.getBlock().getY(), block.getBlock().getZ(), command.name + " " + ChatUtil.toString(args)));
				}
				
				command.onCommand();
			}
		}
		return false;
	}
}
