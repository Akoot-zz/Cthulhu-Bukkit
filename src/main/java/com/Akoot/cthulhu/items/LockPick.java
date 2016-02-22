package com.Akoot.cthulhu.items;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.RandomUtil;
import com.Akoot.cthulhu.utils.Skill;

public class LockPick extends CthItem
{

	private Chest chest;
	private Skill skill;

	public LockPick(Cthulhu plugin, ItemStack item, Player player, Chest chest) 
	{
		super(plugin, item, player);
		this.chest = chest;
		this.skill = Skill.THIEF;
	}

	@Override
	public void init()
	{
		meta.setDisplayName("§dLockpick");
		update();
	}
	
	public int chanceToUnlock()
	{
		int chance = 5;
		int level = user.getLevel(skill);
		if(level >= 10)
		{
			chance = RandomUtil.randomInt(10, level) - 10;
		}
		return chance;
	}

	@Override
	public void use()
	{
		if(meta.getDisplayName().equals("Lockpick")) init();
		setup();
		if(RandomUtil.hasChance(chanceToUnlock()))
		{
			success();
		}
		else
		{
			fail();
		}
		update();
	}

	public void success()
	{
		Inventory inv = chest.getInventory();
		player.openInventory(inv);
		user.levelUp(skill, 30);
	}

	public void fail()
	{
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
		player.getWorld().playEffect(chest.getLocation(), Effect.SMOKE, 2);
		int amt = item.getAmount();
		if(amt > 1) item.setAmount(amt - 1);
		else player.getInventory().removeItem(item);
		user.levelUp(skill, 10);
	}
}
