package com.Akoot.cthulhu.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandRename extends Command
{
	public CommandRename()
	{
		this.color = "&6";
		this.name = "rename";
		this.permission = "cthulhu.rename";
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(args.length == 0)
			{
				sendMessage("Usage: &f/rename <new name>");
				sendMessage("&7&oRenames your item in hand");
			}
			else if(args.length >= 1)
			{
				if((player.getInventory().getItemInMainHand() != null) && (player.getInventory().getItemInMainHand().getType() != Material.AIR))
				{
					String newName = ChatUtil.color("&r" + ChatUtil.toString(args));
					ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
					meta.setDisplayName(newName);
					player.getInventory().getItemInMainHand().setItemMeta(meta);
					sendMessage("Renamed &f" + ChatUtil.itemName(player.getInventory().getItemInMainHand()) + " &6to: &f" + newName);
				}
				else
				{
					sendMessage("&cYou need to hold an item first!");
				}
			}
		}
	}
}
