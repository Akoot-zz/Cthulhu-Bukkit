package com.Akoot.cthulhu.events;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;
import com.Akoot.cthulhu.utils.CthFile;
import com.Akoot.cthulhu.utils.RandomUtil;
import com.earth2me.essentials.Essentials;

public class PlayerEvents implements Listener
{
	private Cthulhu plugin;
	private Essentials ess;

	public PlayerEvents(Cthulhu instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		ess = plugin.getEssentials();
	}

	public void updateItemMoney(ItemStack item)
	{
		if(ess != null)
		{
			if((item != null) && (item.getType() != Material.AIR))
			{
				ItemMeta meta = item.getItemMeta();
				List<String> lore = (meta.getLore() != null ? meta.getLore(): new ArrayList<String>());
				String price = "&8<unknown price>";
				if(ess.getWorth().getPrice(item) != null) 
				{
					price = "&7Base price: &a$" + String.format("%.2f",ess.getWorth().getPrice(item));
					if(price.endsWith(".00"))
					{
						price = price.substring(0, price.indexOf("."));
					}
				}
				price = ChatUtil.color(price);
				if(meta.getLore() != null)
				{
					for(String s: meta.getLore())
					{
						String l = ChatColor.stripColor(s);
						if(l.startsWith("Price: $") || l.equals("<unknown price>"))
						{
							if(!s.equals(price))
							{
								lore.remove(s);
							}
						}
					}
				}
				if(!lore.contains(price))
				{
					lore.add(price);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
		}
	}

	public void updateInventoryMoney(Player player)
	{
		if(plugin.getEssentials() != null)
		{
			Inventory inventory = player.getInventory();
			if(inventory.getContents().length > 0)
			{
				for(ItemStack item: inventory.getContents())
				{
					updateItemMoney(item);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e)
	{
		Player player = (Player) e.getPlayer();
		updateInventoryMoney(player);
	}

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e)
	{
		ItemStack item = e.getItem().getItemStack();
		updateItemMoney(item);
	}

	@EventHandler
	public void onCraft(CraftItemEvent e)
	{
		ItemStack item = e.getCurrentItem();
		updateItemMoney(item);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();

		String format = "";
		String msg = e.getMessage();

		if(plugin.hasPlayerData(player))
		{
			player.setDisplayName(ChatUtil.color(plugin.getPlayerDataFile(player).getString("displayname")));
		}

		msg = msg.replaceAll("%", "%%");

		//ChatColors
		for(ChatColor col: ChatColor.values())
		{
			String instance = "<" + col.name() + ">";
			if(msg.contains(instance) || msg.contains(instance.toLowerCase()))
			{
				msg = msg.replaceAll(instance, "&" + String.valueOf(col.getChar()));
			}
		}

		if(msg.matches("((^|\\s|.*)<(rdm|rand|random|rnd|random-text):\\d.*>(.*|\\s|$))"))
		{
			int i = 0;
			CharSequence inputStr = msg;
			String patternStr = "<(rdm|rand|random|rnd|random-text):\\d.*>";
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(inputStr);
			if(matcher.find())
			{
				i = matcher.start();
			}
			msg = msg.replaceAll("<(rdm|rand|random|rnd|random-text):\\d.*>", RandomUtil.randomString(i));
		}

		if(plugin.getPlayerDataFile(player).has("chat-format"))
		{
			format = plugin.getPlayerDataFile(player).getString("chat-format");
		}
		else
		{
			format = plugin.config.getString("default-chat-format");
		}

		format = format.replaceAll("\\{displayname\\}|\\{dname\\}|\\{nick\\}|\\{nickname\\}", player.getDisplayName());
		format = format.replaceAll("\\{username\\}|\\{name\\}|\\{n\\}|\\{uname\\}", "%1\\$s");
		format = format.replaceAll("\\{message\\}|\\{msg\\}|\\{m\\}|\\{txt\\}", "%2\\$s");

		if(plugin.hasPlugin("Factions"))
		{
			format = format.replaceAll("\\{faction\\}|\\{fac\\}|\\{f\\}", "{factions_relcolor}&l{factions_roleprefix}&r{factions_relcolor}{factions_name\\|rp}");
		}
		else
		{
			format = format.replaceAll("\\{faction\\}|\\{fac\\}|\\{f\\}", "");
		}

		if(plugin.hasPlugin("Vault"))
		{
			String group = plugin.getPermissions().getPrimaryGroup(player);
			format = format.replaceAll("\\{group\\}|\\{gr\\}|\\{g\\}", group);

			String prefix = plugin.getChat().getGroupPrefix(player.getWorld(), group);
			format = format.replaceAll("\\{group-prefix\\}|\\{grpr\\}|\\{p\\}", prefix);
		}
		else
		{
			format = format.replaceAll("\\{group\\}|\\{gr\\}|\\{g\\}", "");
			format = format.replaceAll("\\{group-prefix\\}|\\{grpr\\}|\\{p\\}", "");
		}

		format = ChatUtil.color(format);
		format = format.trim();
		msg = ChatUtil.color(msg);

		e.setFormat(format);
		e.setMessage(msg);
	}

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		if(!plugin.hasPlayerData(player))
		{
			CthFile playerFile = new CthFile(plugin.playerFolder, player.getUniqueId().toString());
			playerFile.create();
			playerFile.set("username", player.getName());
			playerFile.set("displayname", player.getDisplayName());
		}
		else
		{
			if(!plugin.getPlayerDataFile(player).getString("username").equals(player.getName()))
			{
				plugin.getPlayerDataFile(player).set("username", player.getName());
			}
		}
		if(player.hasPlayedBefore())
		{
			if(RandomUtil.hasChance(1, 10))
			{
				ItemStack item = new ItemStack(Material.STICK, 11);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("A Fagggot of Sticks");
				item.setItemMeta(meta);
				player.getInventory().addItem(item);
			}
		}
	}
}
