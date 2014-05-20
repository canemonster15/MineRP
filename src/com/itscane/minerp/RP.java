package com.itscane.minerp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RP implements CommandExecutor {
	
	public ClassSerf serf;
	
	public Main main;
	public RP(Main main) {
		this.main = main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("class")) {
			if(!(sender instanceof Player)) {
				System.out.println("Player is expected!");
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission("minerp.class")) {
					p.sendMessage(ChatColor.RED + "Invalid Permissions!");
				} else {
					if (args.length == 0) {
						//Finish the list of classes someone can be
					} else if(args.length == 1) {
						if (args[0].equalsIgnoreCase("miner")) {
							if (main.players.getString(p.getName() + ".Empire").equalsIgnoreCase("")) {
								p.sendMessage(ChatColor.RED + "You need to be part of an empire!");
							} else {
								String e = main.players.getString(p.getName() + ".Empire");
								serf.add(p, e);
								String ln = main.empires.getString(e + ".Leader");
								Player l = Bukkit.getServer().getPlayer(ln);
								l.sendMessage(ChatColor.BLUE + p.getName() + " has became a serf!");
							}
						}
					}
				}
			}
		}	
		return false;
	}
}
