package me.staartvin.expfly.enchanthandler;

import me.staartvin.expfly.ExpFly;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class PlayerEnchantHandler implements Listener
	{

		ExpFly plugin;

		public PlayerEnchantHandler(ExpFly instance)
			{
				plugin = instance;
				// TODO Auto-generated constructor stub
			}

		@EventHandler
		public void enchantItem(EnchantItemEvent evt)
			{
				Player enchanter = evt.getEnchanter();
				int costExp = evt.getExpLevelCost();
				int currentExp = enchanter.getTotalExperience();

				enchanter.setExp(0);
				enchanter.giveExp((costExp - currentExp));
			}
	}
