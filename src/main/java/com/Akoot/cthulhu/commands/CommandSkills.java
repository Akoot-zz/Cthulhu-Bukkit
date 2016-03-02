package com.Akoot.cthulhu.commands;

import org.bukkit.entity.Player;

import com.Akoot.cthulhu.CthUser;
import com.Akoot.cthulhu.Skill;

public class CommandSkills extends Command
{
	public CommandSkills()
	{
		this.color = "&6";
		this.name = "skills";
		this.permission = "";
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			user = new CthUser(plugin, (Player) sender);
			sendMessage("&6Thief: " + user.getLevel(Skill.THIEF));
		}
	}
}
