package com.Akoot.cthulhu.events;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.ChatUtil;
import com.Akoot.cthulhu.utils.CthFile;
import com.Akoot.cthulhu.utils.RandomUtil;

public class PlayerEvents implements Listener
{
	private Cthulhu plugin;

	public PlayerEvents(Cthulhu instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void updatePlaytime()
	{
		for(Player player: plugin.getServer().getOnlinePlayers())
		{
			int time = plugin.getPlayerDataFile(player).getInt("playtime") + 1;
			plugin.getPlayerDataFile(player).set("playtime", time);
			if(time == 10 || time == 720 || time == 10080)
			{
				player.sendMessage(ChatUtil.color("&dYou have unlocked a new rank!"));
				player.sendMessage(ChatUtil.color("&fType &d/playtime &fto redeem it"));
			}
		}
	}

//	public void removeLore(Inventory inventory)
//	{
//		for(ItemStack item: inventory.getContents())
//		{
//			if((item != null) && (item.getType() != Material.AIR))
//			{
//				ItemMeta meta = item.getItemMeta();
//				meta.setLore(new ArrayList<String>());
//				item.setItemMeta(meta);
//			}
//		}
//	}
//
//	@EventHandler
//	public void onInteract(PlayerInteractEvent e)
//	{
//		Player player = e.getPlayer();
//		if(e.getAction() == Action.LEFT_CLICK_BLOCK)
//		{
//			Block block = e.getClickedBlock();
//			if(block.getType() == Material.CHEST)
//			{
//				Chest chest = (Chest) block.getState();
//				removeLore(chest.getInventory());
//				player.sendMessage("cleared the chest. it is now empty. sorry.");
//			}
//			else
//			{
//				removeLore(player.getInventory());
//				player.sendMessage("your inventory was tasty");
//			}
//		}
//	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();

		String format = "";
		String msg = e.getMessage();

		if(msg.startsWith("\\") || msg.startsWith("./"))
		{
			msg = msg.substring(1);
		}

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

		if(plugin.getPlayerDataFile(player).has("chat-color"))
			msg = "&" + plugin.getPlayerDataFile(player).getString("chat-color") + msg;

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
			playerFile.set("playtime", 0);
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
