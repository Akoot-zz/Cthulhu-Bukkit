package com.Akoot.cthulhu.commands;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandWrite extends Command
{
	public CommandWrite()
	{
		this.color = "&6";
		this.name = "write";
		this.permission = "";
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			Block block = player.getTargetBlock((Set<Material>) null, 4);
			if(block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN))
			{
				Sign sign = (Sign) block.getState();
				if(args.length == 0)
				{
					if(sign.getLines().length > 0)
					{
						for(String line: sign.getLines())
						{
							sendMessage(line);
						}
					}
				}
				else
				{
					String[] a = ChatUtil.toString(args).split(",\\s");
					int i = 0;
					for(String s: a)
					{
						i++;
						sign.setLine(i, ChatUtil.color(s));
					}
					sign.update();
				}
			}
			else
			{
				sendMessage(block.getType().name());
			}
		}
	}
}
