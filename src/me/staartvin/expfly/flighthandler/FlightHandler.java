package me.staartvin.expfly.flighthandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.staartvin.expfly.ExpFly;

import org.bukkit.entity.Player;

public class FlightHandler {

	@SuppressWarnings("unused")
	private ExpFly plugin;
	private List<String> isFlying = new ArrayList<String>();
	private HashMap<String, Integer> taskId = new HashMap<String, Integer>();
	
	public FlightHandler(ExpFly instance) {
		plugin = instance;
	}
	
	public boolean isFlying(String playerName) {
		return isFlying.contains(playerName);
	}
	
	public void enableFlight(Player player) {
		if (!isFlying(player.getName())) {
			isFlying.add(player.getName());
			
			player.setAllowFlight(true);
			player.setFlying(true);
		}
	}
	
	public void disableFlight(Player player) {
		if (isFlying(player.getName())) {
			isFlying.remove(player.getName());
			
			player.setAllowFlight(false);
			player.setFlying(false);
		}
	}
	
	public int getTaskId(String playerName) {
		if (taskId.get(playerName) == null) return -1;
		return taskId.get(playerName);		
	}
	
	public void setTaskId(String playerName, int id) {
		taskId.put(playerName, id);
	}
	
	public int getFlyDuration(Player player, String cost) {
		boolean subtractLevels = false;
		
		if (cost.contains("l")) {
			subtractLevels = true;
			cost = cost.replace("l", "");
		}
		
		int realCost = Integer.parseInt(cost);
		
		if (!subtractLevels) {
			int currentExp = player.getTotalExperience();
			
			int seconds = currentExp / realCost;
			
			return seconds;
		} else {
			int currentExp = player.getLevel();
			
			int seconds = currentExp / realCost;
			
			return seconds;
		}
	}
	
	public void subtractExp(Player player, String cost) {
		boolean subtractLevels = false;
		
		if (cost.contains("l")) {
			subtractLevels = true;
			cost = cost.replace("l", "");
		}
		
		int realCost = Integer.parseInt(cost);
		
		if (!subtractLevels) {
			int currentExp = player.getTotalExperience();
			int leftOver = (currentExp - realCost);
			
			// BUKKIT, Y U HAVE SUCH A WEIRD EXP MANAGER?!
			player.setLevel(0);
			player.setExp(0F);
			player.setTotalExperience(0);
			player.giveExp(leftOver);
			
		} else {
			int currentExp = player.getLevel();
			
			player.setLevel(currentExp - realCost);
		}
	}
}
