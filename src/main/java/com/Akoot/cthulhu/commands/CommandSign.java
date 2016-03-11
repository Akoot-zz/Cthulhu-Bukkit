package com.Akoot.cthulhu.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import com.Akoot.cthulhu.utils.ChatUtil;

public class CommandSign extends Command
{
	public CommandSign()
	{
		this.color = "&5";
		this.name = "sign";
		this.permission = "cthulhu.rename";
	}
	
	@Override
	public void onCommand()
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(args.length == 0)
			{
				if(player.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK)
				{
					BookMeta meta = (BookMeta) player.getInventory().getItemInMainHand().getItemMeta();
					meta.setAuthor(player.getName());
					player.getInventory().getItemInMainHand().setItemMeta(meta);
					sendMessage("Signed \"" +  player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "\" by your name");
				}
				else
				{
					sendMessage("You are not holding a written book");
				}
			}
			else if(args.length >= 1)
			{
				if(player.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK)
				{
					String author = ChatUtil.color(ChatUtil.toString(args));
					BookMeta meta = (BookMeta) player.getInventory().getItemInMainHand().getItemMeta();
					meta.setAuthor(author);
					player.getInventory().getItemInMainHand().setItemMeta(meta);
					sendMessage("Signed \"" +  player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "\" by " + author);
				}
				else
				{
					sendMessage("Error: &fYou are not holding a written book");
				}
			}
		}
	}
}
