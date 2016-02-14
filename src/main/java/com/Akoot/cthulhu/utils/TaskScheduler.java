package com.Akoot.cthulhu.utils;

import java.lang.reflect.Method;

import org.bukkit.scheduler.BukkitRunnable;

import com.Akoot.cthulhu.Cthulhu;

public class TaskScheduler extends BukkitRunnable
{
	private Method method;
	private Object classe;
	private Cthulhu plugin;

	public TaskScheduler(Cthulhu plugin)
	{
		this.plugin = plugin;
	}
	/**
	 * @param classe the class
	 * @param time time in seconds
	 */
	public void schedule(Object classe, String method, int time)
	{
		plugin.log.info(classe.getClass().getName() + "." + method + "() will be called every " + time + " seconds");
		this.classe = classe;
		this.runTaskTimer(plugin, 0L, 20L*time);
		try
		{
			this.method = classe.getClass().getDeclaredMethod(method);
		}
		catch(Exception e)
		{
			plugin.log.severe("Method not found: " + method);
		}
	}

	@Override
	public void run() 
	{
		if(method != null && classe != null)
		{
			try
			{
				method.invoke(classe);
			}
			catch(Exception e)
			{
				plugin.log.severe(classe.getClass().getName() + " could not invoke " + method.getName());
			}
		}
	}
}
