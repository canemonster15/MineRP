package com.itscane.minerp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sell implements CommandExecutor {

	public Main main;

	public Sell(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (cmd.getName().equalsIgnoreCase("sell")) {
			if (!(sender instanceof Player)) {
				System.out.println("Player is expected!");
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission("minerp.sell")) {
					p.sendMessage(ChatColor.RED + "Invalid Permissions!");
				} else {
					if (args.length == 0) {
						// Finish the help page for items they can sell
					} else if (args.length == 1) {
						
					} else if (args.length >= 2) {
						
					}
				}
			}
		}

		return false;
	}

}
