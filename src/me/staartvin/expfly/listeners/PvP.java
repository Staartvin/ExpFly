package me.staartvin.expfly.listeners;

import me.staartvin.expfly.ExpFly;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvP implements Listener {

	private ExpFly plugin;
	
	public PvP(ExpFly instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPvP(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
			return;
		}
		
		Player damager = (Player) event.getDamager();
		
		if (plugin.getMainConfig().isPvPAllowed()) return;
		
		if (plugin.getFlightHandler().isFlying(damager.getName())) {
			
			damager.sendMessage(ChatColor.RED + "You cannot PvP because you are in fly mode!");
			event.setDamage(0);
			event.setCancelled(true);
			return;
		}
	}
}
