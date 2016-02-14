package com.Akoot.cthulhu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RandomUtil
{
	public static Random random = new Random();
	
	public static boolean hasChance(int a, int b)
	{
		int x = randomInt(a, b);
		int y = randomInt(a, b);
		return x == y;
	}
	
	public static boolean hasChance(double percent)
	{
		double p = Math.random() * 100;
		return p < percent;
	}
	
	public static int randomInt(int a, int b)
	{
		int i = random.nextInt((b - a) + 1) + a;
		return i;
	}
	
	public static String randomPlayer()
	{
		List<String> names = new ArrayList<String>();
		for(Player p: Bukkit.getServer().getOnlinePlayers())
		{
			names.add(p.getName());
		}
		for(OfflinePlayer p: Bukkit.getServer().getOfflinePlayers())
		{
			names.add(p.getName());
		}
		if(names.isEmpty()) names.add("<player>");
		return names.get(random.nextInt(names.size() - 1));
	}
	
	public static String randomString(int range)
	{
		String s = "";
		for(int i = 0; i < range; i++)
		{
			int r = randomInt(32, 126);
			s += (char) r;
		}
		return s;
	}
}
