package me.staartvin.expfly.listeners;

import me.staartvin.expfly.ExpFly;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

	private ExpFly plugin;

	public PlayerQuit(ExpFly instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		if (plugin.getFlightHandler().isFlying(player.getName())) {
			plugin.getServer()
					.getScheduler()
					.cancelTask(
							plugin.getFlightHandler().getTaskId(
									player.getName()));
			
			plugin.getFlightHandler().disableFlight(player);
		}
	}
}
