package com.Akoot.cthulhu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;
import com.Akoot.cthulhu.utils.CthUser;

public class XPOrb
{
	public Player player;
	public CthUser user;
	public ItemStack orb;
	public ItemMeta meta;
	public List<String> lore;
	public String name;

	public int storedXP;	
	public int maxXP;
	public int power;
	public Cthulhu plugin;

	public XPOrb(Cthulhu plugin, ItemStack item, Player player)
	{
		this.plugin = plugin;
		this.player = player;
		this.user = new CthUser(player);
		this.orb = item;
		this.meta = item.getItemMeta();
		this.lore = (this.meta.getLore() != null ? this.meta.getLore() : new ArrayList<String>());
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

	private void init()
	{
		String newName = "§a" + name + " §2(empty)";
		lore.add("§6Stored: §e0");
		meta.setDisplayName(newName);
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		trace("&aInitiated " + name);
		store(user.getTotalExperience());
	}

	private void setup()
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
				//tests
				else
				{
					storedXP = 0;
					//trace(line);
					//trace("Stored: 1");
				}
			}
		}
	}

	private void update()
	{
		meta.setLore(lore);
		orb.setItemMeta(meta);
	}

	private void store(int xp)
	{
		if(storedXP < maxXP)
		{
			//trace("&axp stored: " + storedXP);
			//trace("&6max xp: " + maxXP);
			int xpLost = (xp > maxXP ? xp - maxXP : 0);
			storedXP += xp - xpLost;

			//tests
//			trace("xp-to-store: " + xp);
//			trace("max: " + maxXP);
//			trace("xp-stored: " + storedXP);
//			trace("xp-lost: " + xpLost);

			int levels = user.getLevel(storedXP);

			String info = levels + ChatUtil.toPlural(levels, " level");
			if(storedXP >= maxXP) info = "full";
			if(storedXP <= 0) info = "empty";

			meta.setDisplayName("§a" + name + " §2(" + info + ")");
			lore.set(0, "§6Stored: §e" + storedXP);

			user.setTotalExperience(xp - storedXP);
			//trace("&axp stored now: " + storedXP);
		}
	}

	private void trace(String s)
	{
		player.sendMessage(ChatUtil.color(s));
	}

	private void drain()
	{
		user.setTotalExperience(user.getTotalExperience() + storedXP);
		meta.setDisplayName("§a" + name + " §2(empty)");
		lore.set(0, "§6Stored: §e0");
	}
}
