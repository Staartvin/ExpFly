package me.staartvin.expfly.listeners;

import me.staartvin.expfly.ExpFly;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

	private ExpFly plugin;
	
	public PlayerDeath(ExpFly instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		if (plugin.getFlightHandler().isFlying(player.getName())) {
			if (plugin.getMainConfig().doDisableOnDeath()) {
				plugin.getServer().getScheduler().cancelTask(plugin.getFlightHandler().getTaskId(player.getName()));
				
				plugin.getFlightHandler().disableFlight(player);
				
				player.sendMessage(ChatColor.RED + "Flying is now disabled because you died!");
			}
		}
	}
}
