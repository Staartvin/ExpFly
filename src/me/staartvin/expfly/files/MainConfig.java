package me.staartvin.expfly.files;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.expfly.ExpFly;

import org.bukkit.configuration.file.FileConfiguration;

public class MainConfig {

	private ExpFly plugin;

	private FileConfiguration config;

	public MainConfig(ExpFly instance) {
		plugin = instance;
	}

	public void loadConfig() {
		config = plugin.getConfig();
		config.options()
				.header("ExpFly v"
						+ plugin.getDescription().getVersion()
						+ " Config"
						+ "\n'xp cost per second' is the cost (in exp) that a player pays to fly for 1 second"
						+ "\nWhen you add a 'l' to the number, it will be a level. So '10' will be 10 xp, but '10l' will be 10 levels of xp"
						+ "\nIf 'disable on death' is true, fly mode will be turned off if a player dies."
						+ "\n'Banned players' is a list of banned players that cannot fly.");

		// Seconds of how long a player can fly before one level is taken
		config.addDefault("xp cost per second", "1l");

		// Disable flight when a person died
		config.addDefault("disable on death", true);

		// List of banned players
		config.addDefault("banned players", new ArrayList<String>());

		// Allow or disallow pvp in fly mode
		config.addDefault("allow while flying.pvp", false);

		// Allow or disallow shooting in fly mode
		config.addDefault("allow while flying.bow", false);

		// Allow or disallow throwing potions in fly mode
		config.addDefault("allow while flying.throwing potions", false);
		
		// Remove old value
		config.set("seconds per level", null);

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}

	public String getXpCostPerSecond() {
		return config.get("xp cost per second").toString();
	}

	public boolean doDisableOnDeath() {
		return config.getBoolean("disable on death");
	}

	public List<String> getBannedPlayers() {
		return config.getStringList("banned players");
	}
	
	public boolean isPvPAllowed() {
		return config.getBoolean("allow while flying.pvp");
	}
	
	public boolean isBowAllowed() {
		return config.getBoolean("allow while flying.bow");
	}
	
	public boolean arePotionsAllowed() {
		return config.getBoolean("allow while flying.throwing potions");
	}
	
	public void reload() {
		plugin.reloadConfig();
		plugin.saveConfig();
		loadConfig();
	}
}
