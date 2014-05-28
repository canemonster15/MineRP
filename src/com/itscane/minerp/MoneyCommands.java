package com.itscane.minerp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommands implements CommandExecutor {
	
	public Main main;
	public MoneyCommands(Main main) {
		this.main = main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("money")) {
			if(!(sender instanceof Player)) {
				System.out.println("Player is expected!");
			} else {
				Player p = (Player) sender;
				if(!p.hasPermission("minerp.money")) {
					p.sendMessage(ChatColor.RED + "Invalid Permissions!");
				} else {
					if (args.length == 0) {
						//Money help page
					} else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("pay")) {
							p.sendMessage(ChatColor.RED + "You need to specify the player!");
						} else {
							p.sendMessage(ChatColor.RED + "Argument not recognized!");
						}
					} else if(args.length == 2) {
						if (args[0].equalsIgnoreCase("pay")) {
							p.sendMessage(ChatColor.RED + "You nee to specify the amount!");
						} else {
							p.sendMessage(ChatColor.RED + "Unrecognized argument!");
						}
					} else if (args.length == 3) {
						if (args[0].equalsIgnoreCase("pay")) {
							Player t = Bukkit.getServer().getPlayer(args[1]);
							if (t == null) {
								p.sendMessage(ChatColor.RED + "Player not found!");
							} else {
								try {
									double a = Double.parseDouble(args[2]);
									if (main.players.getDouble(p.getName() + ".Wallet") < a) {
										p.sendMessage(ChatColor.RED + "You don't have enough money!");
									} else {
										pay(p, t, a);
									}
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public void pay (Player s, Player t, double a) {
		double ca = main.players.getDouble(s.getName() + ".Wallet");
		double na = ca - a;
		main.players.set(s.getName() + ".Wallet", na);
		double ca2 = main.players.getDouble(t.getName() + ".Wallet");
		double na2 = ca2 + a;
		main.players.set(t.getName() + ".Wallet", na2);
		main.save();
	}

}
