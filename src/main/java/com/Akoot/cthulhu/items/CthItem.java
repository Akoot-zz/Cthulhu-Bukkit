package com.Akoot.cthulhu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Akoot.cthulhu.CthUser;
import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;

public class CthItem 
{
	public Player player;
	public CthUser user;
	public ItemStack item;
	public ItemMeta meta;
	public List<String> lore;
	public String name;

	public Cthulhu plugin;

	public CthItem(Cthulhu plugin, ItemStack item, Player player)
	{
		this.plugin = plugin;
		this.player = player;
		this.user = new CthUser(plugin, player);
		this.item = item;
		this.meta = (item.hasItemMeta() ? item.getItemMeta() : null);
		if(meta != null)
		{
			this.lore = (this.meta.getLore() != null ? this.meta.getLore() : new ArrayList<String>());
		}
		else
		{
			this.lore = new ArrayList<String>();
		}
	}

	public void use(){}

	public void init(){}

	public void setup(){}

	public void update()
	{
		if(meta != null)
		{
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
	}

	public void trace(String s)
	{
		player.sendMessage(ChatUtil.color(s));
	}
}
