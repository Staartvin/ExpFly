package me.staartvin.expfly.listeners;

import me.staartvin.expfly.ExpFly;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class PlayerShootArrow implements Listener {

	private ExpFly plugin;
	
	public PlayerShootArrow(ExpFly instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onShot(EntityShootBowEvent event) {
		
		if (event.isCancelled()) return;
		
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		
		if (plugin.getMainConfig().isBowAllowed()) return;
		
		if (plugin.getFlightHandler().isFlying(player.getName())) {
			
			player.sendMessage(ChatColor.RED + "You cannot use your bow because you are in fly mode!");
			event.setCancelled(true);
			return;
		}
	}
}
