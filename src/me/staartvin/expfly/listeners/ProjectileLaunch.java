package me.staartvin.expfly.listeners;

import me.staartvin.expfly.ExpFly;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunch implements Listener {

	private ExpFly plugin;
	
	public ProjectileLaunch(ExpFly instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onLaunch(ProjectileLaunchEvent event) {
		Projectile projectile = event.getEntity();
		
		if (event.isCancelled()) return;
		
		if (!projectile.getType().equals(EntityType.SPLASH_POTION)) return;
		
		if (plugin.getMainConfig().arePotionsAllowed()) return;
		
		if (projectile.getShooter() == null || !(projectile.getShooter() instanceof Player)) return;
		
		Player player = (Player) projectile.getShooter();
		
		if (plugin.getFlightHandler().isFlying(player.getName())) {
			
			player.sendMessage(ChatColor.RED + "You cannot throw potions because you are in fly mode!");
			event.setCancelled(true);
			return;
		}
	}
}
