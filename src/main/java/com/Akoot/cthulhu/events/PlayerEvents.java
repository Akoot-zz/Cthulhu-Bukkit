package com.Akoot.cthulhu.events;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.items.Hand;
import com.Akoot.cthulhu.items.LockPick;
import com.Akoot.cthulhu.items.XPOrb;
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
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		Player player = event.getPlayer();
		String cmd = event.getMessage();
		plugin.commandLog.addLine("[" + ChatUtil.getCurrentTime() + "] " + player.getName() + ": " + cmd);
	}

	public void updatePlaytime()
	{
		for(Player player: plugin.getServer().getOnlinePlayers())
		{
			if(plugin.getEssentials() != null)
			{
				if(!plugin.getEssentials().getUser(player).isAfk())
				{
					if(plugin.getPlayerDataFile(player).has("playtime"))
					{
						int time = plugin.getPlayerDataFile(player).getInt("playtime") + 1;
						plugin.getPlayerDataFile(player).set("playtime", time);
						if(plugin.getPermissions() != null)
						{
							if(time == 10 || time == 720 || time == 10080)
							{
								player.sendMessage(ChatUtil.color("&dYou have unlocked a new rank!"));
								player.sendMessage(ChatUtil.color("&fType &d/playtime &fto redeem it"));
								if(time == 10)
								{
									plugin.getPermissions().playerAdd(player, "redeem.group.member");
								}
								else if(time == 720)
								{
									plugin.getPermissions().playerAdd(player, "redeem.group.member+");
								}
								else if(time == 10080)
								{
									plugin.getPermissions().playerAdd(player, "redeem.group.loyalist");
								}
							}
						}
					}
					else
					{
						plugin.getPlayerDataFile(player).set("playtime", 0);
					}
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		if((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			ItemStack item = player.getItemInHand();
			if(item.getType() == Material.EMERALD && item.hasItemMeta())
			{
				ItemMeta meta = item.getItemMeta();
				String name = (meta.hasDisplayName() ? ChatColor.stripColor(meta.getDisplayName()) : "Emerald");
				if(name.matches(plugin.config.getString("xp-orb") + ".*"))
				{
					XPOrb orb = new XPOrb(plugin, item, player);
					orb.use();
				}
			}

			if(item.getType() == Material.BLAZE_ROD && item.hasItemMeta())
			{
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK )
				{
					Block block = e.getClickedBlock();
					if(block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST)
					{
						ItemMeta meta = item.getItemMeta();
						String name = (meta.hasDisplayName() ? ChatColor.stripColor(meta.getDisplayName()) : "Blaze Rod");
						if(name.matches(plugin.config.getString("lockpick") + ".*"))
						{
							Chest chest = (Chest) block.getState();
							LockPick pick = new LockPick(plugin, item, player, chest);
							pick.use();
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e)
	{
		Player player = e.getPlayer();
		if(player.isSneaking())
		{
			int level = (plugin.getPlayerDataFile(player).has("thief") ? plugin.getPlayerDataFile(player).getInt("thief") : 0);
			Entity entity = e.getRightClicked();
			if(entity.isValid())
			{
				if(entity instanceof Player)
				{
					Player target = (Player) entity;
					Hand hand = new Hand(plugin, player, target);
					hand.use();
				}
			}
			plugin.getPlayerDataFile(player).set("thief", level);
		}
	}

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

		plugin.chatLog.addLine("[" + ChatUtil.getCurrentTime() + "] " + player.getName() + ": " + ChatColor.stripColor(e.getMessage()));
	}

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		if(player.isOp())
		{
			player.chat("/b 0");
			player.chat("/v 0");
			player.chat("/vr 0");
			player.chat("/b v mm");
		}
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
	}
}
