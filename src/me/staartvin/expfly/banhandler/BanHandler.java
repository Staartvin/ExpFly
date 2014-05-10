package me.staartvin.expfly.banhandler;

import java.util.List;

import me.staartvin.expfly.ExpFly;

public class BanHandler {

	private ExpFly plugin;
	
	public BanHandler(ExpFly instance) {
		plugin = instance;
	}
	
	public boolean isBanned(String playerName) {
		List<String> banned = plugin.getMainConfig().getBannedPlayers();
		
		for (String player: banned) {
			if (player.equalsIgnoreCase(playerName)) return true;
		}
		return false;
	}
	
	public void ban(String playerName) {
		List<String> banned = plugin.getMainConfig().getBannedPlayers();
		
		banned.add(playerName);
		
		plugin.getConfig().set("banned players", banned);
		plugin.saveConfig();
	}
	
	public void unban(String playerName) {
		List<String> banned = plugin.getMainConfig().getBannedPlayers();
		
		banned.remove(playerName);
		
		plugin.getConfig().set("banned players", banned);
		plugin.saveConfig();
	}
}
