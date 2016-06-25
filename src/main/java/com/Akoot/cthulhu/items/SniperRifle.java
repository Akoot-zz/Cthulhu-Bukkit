package com.Akoot.cthulhu.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Akoot.cthulhu.Cthulhu;

public class SniperRifle extends CthItem
{

	public SniperRifle(Cthulhu plugin, ItemStack item, Player player)
	{
		super(plugin, item, player);
	}
	
	public void shoot()
	{
		trace("you shoot");
	}
	
	public void scope()
	{
		trace("you zoom in");
	}

}
