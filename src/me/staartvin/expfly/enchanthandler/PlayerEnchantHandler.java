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
				int newLevel = enchanter.getLevel() - evt.getExpLevelCost();
				int newTotal = convertLevelToExp(newLevel);
				newTotal += Math.floor(enchanter.getExp() * newLevel);
				enchanter.setTotalExperience(0);
				enchanter.setLevel(0);
				enchanter.setExp(0);
				enchanter.giveExp(newTotal);
				evt.setExpLevelCost(0);
			}

		private int convertLevelToExp(int newLevel)
			{
				if (newLevel >= 30)
					{
						return (int) (3.5 * (newLevel * newLevel) - (151.5 * newLevel) + 2220);
					}
				else if (newLevel >= 15)
					{
						return (int) (1.5 * (newLevel * newLevel) - (29.5 * newLevel) + 360);

					}
				else
					{
						return 17 * newLevel;
					}

			}
	}