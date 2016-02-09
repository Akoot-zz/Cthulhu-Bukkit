package com.Akoot.cthulhu.utils;

import java.util.Random;

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
