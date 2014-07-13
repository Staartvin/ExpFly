package me.staartvin.expfly;

import me.staartvin.expfly.banhandler.BanHandler;
import me.staartvin.expfly.commands.Commands;
import me.staartvin.expfly.enchanthandler.PlayerEnchantHandler;
import me.staartvin.expfly.files.MainConfig;
import me.staartvin.expfly.flighthandler.FlightHandler;
import me.staartvin.expfly.listeners.PlayerDeath;
import me.staartvin.expfly.listeners.PlayerQuit;
import me.staartvin.expfly.listeners.PlayerShootArrow;
import me.staartvin.expfly.listeners.ProjectileLaunch;
import me.staartvin.expfly.listeners.PvP;

import org.bukkit.plugin.java.JavaPlugin;

public class ExpFly extends JavaPlugin {

	private MainConfig config = new MainConfig(this);
	private FlightHandler flightHandler = new FlightHandler(this);
	private BanHandler banHandler = new BanHandler(this);
	
	public void onEnable() {
		// Load config
		config.loadConfig();
		
		// Setup commands
		getCommand("xpfly").setExecutor(new Commands(this));
		
		// Setup listeners
		getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
		getServer().getPluginManager().registerEvents(new PvP(this), this);
		getServer().getPluginManager().registerEvents(new PlayerShootArrow(this), this);
		getServer().getPluginManager().registerEvents(new ProjectileLaunch(this), this);
		getServer().getPluginManager().registerEvents(new PlayerEnchantHandler(this), this);
		
		getLogger().info("ExpFly " + getDescription().getVersion() + " has been enabled!");
	}
	
	public void onDisable() {
		
		// To avoid deadlocks or copies of tasks
		getServer().getScheduler().cancelTasks(this);
		
		getLogger().info("ExpFly " + getDescription().getVersion() + " has been disabled!");
	}
	
	public MainConfig getMainConfig() {
		return config;
	}
	
	public FlightHandler getFlightHandler() {
		return flightHandler;
	}
	
	public BanHandler getBanHandler() {
		return banHandler;
	}
}
