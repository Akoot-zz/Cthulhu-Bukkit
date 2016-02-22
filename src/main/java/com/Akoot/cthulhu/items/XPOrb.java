package com.Akoot.cthulhu.items;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;

public class XPOrb extends CthItem
{
	private String name;
	private int storedXP;	
	private int maxXP;
	private int power;

	public XPOrb(Cthulhu plugin, ItemStack item, Player player)
	{
		super(plugin, item, player);
		this.name = plugin.config.getString("xp-orb");
		this.power = 0;
		this.storedXP = 0;
		this.maxXP = -1;
	}

	public void use()
	{
		setup();
		if(lore.size() > 0)
		{
			if(user.getTotalExperience() > 0)
			{
				if(storedXP < maxXP)
				{
					store(user.getTotalExperience());
				}
				else
				{
					drain();
				}
			}
			else
			{
				drain();
			}
		}
		else
		{
			init();
		}
		update();
	}
	
	@Override
	public void init()
	{
		String newName = "§a" + name + " §2(empty)";
		lore.add("§6Stored: §e0");
		meta.setDisplayName(newName);
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		trace("&aInitiated " + name);
		store(user.getTotalExperience());
	}
	
	@Override
	public void setup()
	{
		power = (meta.hasEnchant(Enchantment.ARROW_DAMAGE) ? meta.getEnchantLevel(Enchantment.ARROW_DAMAGE) : 1);
		maxXP = 256 * power;
		name = plugin.config.getString("xp-orb");
		if(!lore.isEmpty())
		{
			for(String line: lore)
			{
				line = ChatColor.stripColor(line);
				if(line.matches("Stored:\\s\\d.*"))
				{
					storedXP = Integer.valueOf(line.substring(line.indexOf(":") + 2));
				}
				else
				{
					storedXP = 0;
				}
			}
		}
	}

	private void store(int xp)
	{
		if(storedXP < maxXP)
		{
			int xpLost = (xp > maxXP ? xp - maxXP : 0);
			storedXP += xp - xpLost;

			int levels = user.getLevel(storedXP);

			String info = levels + ChatUtil.toPlural(levels, " level");
			if(storedXP >= maxXP) info = "full";
			if(storedXP <= 0) info = "empty";

			meta.setDisplayName("§a" + name + " §2(" + info + ")");
			lore.set(0, "§6Stored: §e" + storedXP);

			user.setTotalExperience(xp - storedXP);
		}
	}

	private void drain()
	{
		user.setTotalExperience(user.getTotalExperience() + storedXP);
		meta.setDisplayName("§a" + name + " §2(empty)");
		lore.set(0, "§6Stored: §e0");
	}
}
