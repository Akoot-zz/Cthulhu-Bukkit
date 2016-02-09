package com.Akoot.cthulhu.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandSetlore extends Command
{
	public CommandSetlore()
	{
		this.color = "&d";
		this.name = "setlore";
		this.permission = "cthulhu.rename";
	}

	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(args.length > 0)
			{
				if((player.getItemInHand() != null) && (player.getItemInHand().getType() != Material.AIR))
				{
					List<String> lore = new ArrayList<String>();
					String msg = ChatUtil.toString(args);
					for(String s: msg.split(",\\s"))
					{
						lore.add(ChatUtil.color("&f" + s).trim());
					}
					ItemStack item = player.getItemInHand();
					ItemMeta meta = item.getItemMeta();
					meta.setLore(lore);
					item.setItemMeta(meta);
					sendMessage("Added lore:");
					for(String s: lore)
					{
						sendMessage("&f" + s);
					}
					meta = player.getItemInHand().getItemMeta();
				}
				else
				{
					sendMessage("&cYou need to hold an item first!");
				}
			}
			else
			{
				sendMessage("Usage: &f/setlore <lore>");
				sendMessage("Example: &f/setlore line1, line2, line3");
			}
		}
	}
}
