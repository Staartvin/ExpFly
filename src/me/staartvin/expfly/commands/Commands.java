package me.staartvin.expfly.commands;

import me.staartvin.expfly.ExpFly;
import me.staartvin.expfly.flighthandler.FlyingCounterTask;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Commands implements CommandExecutor {

	private ExpFly plugin;

	public Commands(ExpFly instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// TODO Auto-generated method stub

		// /xpfly
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Computers can't fly!");
				return true;
			}

			Player player = (Player) sender;
			String playerName = player.getName();

			if (!hasPermission("expfly.fly", sender))
				return true;

			if (plugin.getBanHandler().isBanned(sender.getName())) {
				sender.sendMessage(ChatColor.RED + "You cannot fly. You have been banned from flying.");
				return true;
			}
			
			if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
				sender.sendMessage(ChatColor.RED + "You can only fly in survival mode!");
				return true;
			}

			// Disable flying
			if (plugin.getFlightHandler().isFlying(playerName)) {
				plugin.getFlightHandler().disableFlight(player);

				if (plugin.getFlightHandler().getTaskId(player.getName()) == -1) {
					sender.sendMessage(ChatColor.RED
							+ "ERROR: There is no timer running.");
					return true;
				}

				// Cancel task so it stops taking XP.
				plugin.getServer()
						.getScheduler()
						.cancelTask(
								plugin.getFlightHandler().getTaskId(
										player.getName()));

				sender.sendMessage(ChatColor.GREEN + "Flying is now disabled!");
				return true;
			} else {
				
				if (player.getLevel() <= 0 || player.getTotalExperience() <= 0) {
					player.sendMessage(ChatColor.RED
							+ "You don't have enough xp to fly at the moment!");
					return true;
				}
				
				// Enable flying
				plugin.getFlightHandler().enableFlight(player);

				BukkitTask task = new FlyingCounterTask(plugin, player)
						.runTaskTimer(plugin, 0L, 20L);

				// Store taskId to use for later use
				plugin.getFlightHandler().setTaskId(player.getName(),
						task.getTaskId());

				sender.sendMessage(ChatColor.GREEN + "Flying is now enabled!");
				sender.sendMessage(ChatColor.YELLOW
						+ "You can fly for "
						+ ChatColor.RED
						+ plugin.getFlightHandler().getFlyDuration(player, plugin.getMainConfig().getXpCostPerSecond()) + ChatColor.YELLOW + " seconds!");
				return true;
			}

		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				if (!hasPermission("expfly.help", sender))
					return true;

				showHelpPage(1, sender);
				return true;
			} else if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(ChatColor.BLUE
						+ "-----------------------------------------------------");
				sender.sendMessage(ChatColor.GOLD + "Developed by: "
						+ ChatColor.GRAY + plugin.getDescription().getAuthors());
				sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GRAY
						+ plugin.getDescription().getVersion());
				sender.sendMessage(ChatColor.YELLOW
						+ "Type /xpfly help for a list of commands.");
				return true;
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("ban")) {
				if (!hasPermission("expfly.ban", sender)) return true;
				
				String target = args[1];
				
				if (plugin.getBanHandler().isBanned(target)) {
					sender.sendMessage(ChatColor.YELLOW + target + ChatColor.RED + " is already banned!");
					return true;
				}
				
				plugin.getBanHandler().ban(target);
				
				sender.sendMessage(ChatColor.GREEN + "You have banned " + ChatColor.YELLOW + target + ChatColor.GREEN + " from flying.");
				return true;
			} else if (args[0].equalsIgnoreCase("unban")) {
				if (!hasPermission("expfly.unban", sender)) return true;
				
				String target = args[1];
				
				if (!plugin.getBanHandler().isBanned(target)) {
					sender.sendMessage(ChatColor.YELLOW + target + ChatColor.RED + " is not banned!");
					return true;
				}
				
				plugin.getBanHandler().unban(target);
				
				sender.sendMessage(ChatColor.GREEN + "You have unbanned " + ChatColor.YELLOW + target + ChatColor.GREEN + " from flying.");
				return true;
			} else if (args[0].equalsIgnoreCase("help")) {
				if (!hasPermission("expfly.help", sender))
					return true;

				Integer id = -1;

				try {
					id = Integer.parseInt(args[1]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED
							+ (args[1] + " is not a valid page number!"));
					return true;
				}

				showHelpPage(id, sender);
				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (!hasPermission("expfly.reload", sender)) {
					return true;
				}
				
				plugin.getMainConfig().reload();
				
				sender.sendMessage(ChatColor.GREEN + "ExpFly reloaded!");
				return true;
			}
		}
		sender.sendMessage(ChatColor.RED + "Unknown command!");
		sender.sendMessage(ChatColor.YELLOW
				+ "Use '/xpfly help' to get a list of commands.");
		return true;
	}

	public boolean hasPermission(String permission, CommandSender sender) {
		if (sender.hasPermission(permission))
			return true;
		else {
			sender.sendMessage(ChatColor.RED + "You need " + permission
					+ " to do this.");
			return false;
		}
	}
	
	private void showHelpPage(int page, CommandSender sender) {
		int maximumPages = 1;
		if (page == 1) {
			sender.sendMessage(ChatColor.BLUE + "-------------------["
					+ ChatColor.GOLD + "ExpFly" + ChatColor.BLUE
					+ "]----------------------");
			sender.sendMessage(ChatColor.GOLD + "/xpfly"
					+ ChatColor.BLUE + " --- Toggle flying mode");
			sender.sendMessage(ChatColor.GOLD + "/xpfly ban <player>"
					+ ChatColor.BLUE + " --- Ban a player from flying");
			sender.sendMessage(ChatColor.GOLD + "/xpfly unban <player>"
					+ ChatColor.BLUE + " --- Unban a player from flying");
			sender.sendMessage(ChatColor.GOLD + "/xpfly info"
					+ ChatColor.BLUE + " --- Get information about ExpFly");
			sender.sendMessage(ChatColor.GOLD + "/xpfly reload"
					+ ChatColor.BLUE + " --- Reload ExpFly");
			sender.sendMessage(ChatColor.GOLD + "Page " + ChatColor.BLUE + "1 "
					+ ChatColor.GOLD + "of " + ChatColor.BLUE + maximumPages);
		} else {
			sender.sendMessage(ChatColor.BLUE + "-------------------["
					+ ChatColor.GOLD + "ExpFly" + ChatColor.BLUE
					+ "]----------------------");
			sender.sendMessage(ChatColor.GOLD + "/xpfly"
					+ ChatColor.BLUE + " --- Toggle flying mode");
			sender.sendMessage(ChatColor.GOLD + "/xpfly ban <player>"
					+ ChatColor.BLUE + " --- Ban a player from flying");
			sender.sendMessage(ChatColor.GOLD + "/xpfly unban <player>"
					+ ChatColor.BLUE + " --- Unban a player from flying");
			sender.sendMessage(ChatColor.GOLD + "/xpfly info"
					+ ChatColor.BLUE + " --- Get information about ExpFly");
			sender.sendMessage(ChatColor.GOLD + "/xpfly reload"
					+ ChatColor.BLUE + " --- Reload ExpFly");
			sender.sendMessage(ChatColor.GOLD + "Page " + ChatColor.BLUE + "1 "
					+ ChatColor.GOLD + "of " + ChatColor.BLUE + maximumPages);
		}
	}

}
