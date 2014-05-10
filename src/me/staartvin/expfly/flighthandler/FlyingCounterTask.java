package me.staartvin.expfly.flighthandler;

import me.staartvin.expfly.ExpFly;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FlyingCounterTask extends BukkitRunnable {

	final Player player;
	private ExpFly plugin;
	boolean sendWarning = false;

	public FlyingCounterTask(ExpFly plugin, final Player player) {
		this.player = player;
		this.plugin = plugin;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// Decrease players experience
		if (player.getLevel() <= 0) {
			
			plugin.getFlightHandler().disableFlight(player);
			
			player.sendMessage(ChatColor.RED + "You ran out of xp and flying is now disabled.");
			
			this.cancel();
			
			return;
		}
		
		if (player.getLevel() <= 2 && !sendWarning) {
			player.sendMessage(ChatColor.RED + "WARNING: You are low on xp!");
			sendWarning = true;
		}
		
		plugin.getFlightHandler().subtractExp(player, plugin.getMainConfig().getXpCostPerSecond());
	}

}
