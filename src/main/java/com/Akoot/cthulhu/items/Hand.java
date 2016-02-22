package com.Akoot.cthulhu.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.Akoot.cthulhu.Cthulhu;
import com.Akoot.cthulhu.utils.RandomUtil;
import com.Akoot.cthulhu.utils.Skill;

public class Hand extends CthItem
{
	private Player target;
	private Skill skill;

	public Hand(Cthulhu plugin, Player player, Player target) 
	{
		super(plugin, player.getItemInHand(), player);
		this.target = target;
		this.skill = Skill.THIEF;
	}
	
	@Override
	public void update(){}

	@Override
	public void use()
	{
		init();
		if(user.getLevel(skill) >= 30)
		{
			if((!target.isSprinting() && !target.isBlocking() && (!target.isOp() || player.isOp())) || (target.isConversing()))
			{
				Inventory pocket = target.getInventory();
				player.openInventory(pocket);
				if(!RandomUtil.hasChance(user.getLevel(skill)))
				{
					trace("&8" + target.getName() + " knows you are pickpocketing them...");
					target.sendMessage("§oYou feel a hand in your pocket...");
					player.closeInventory();
				}
				user.levelUp(skill, 25);
			}
		}
	}

	@Override
	public void setup() {}
}
