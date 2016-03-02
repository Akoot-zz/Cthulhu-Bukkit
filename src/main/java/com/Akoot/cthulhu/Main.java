package com.Akoot.cthulhu;

import com.Akoot.cthulhu.util.CthFile;

public class Main 
{
	public static void main(String[] args)
	{
		CthFile c = new CthFile("gmail");
		if(!c.exists()) c.create();
		
		//trace(c.get("nigga"));
		//trace(c.getList("names"));
		c.addTo("names", "jacob");
		c.addTo("names", "jake");
		c.addTo("names", "spaghett");
		trace(c.getList("names"));
	}

	public static void trace(Object o)
	{
		System.out.println(o);
	}
}
