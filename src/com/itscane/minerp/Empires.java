package com.itscane.minerp;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Empires implements CommandExecutor {

	public static HashMap<String, String> invites = new HashMap<String, String>();

	public Main main;

	public Empires(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (cmd.getName().equalsIgnoreCase("empires")) {
			if (!(sender instanceof Player)) {
				System.out.println("Player is expected!");
			} else {
				Player p = (Player) sender;
				if (args.length == 0) {
					// Add help page here
				} else if (args.length == 1) {
					// Creating a Empire
					if (args[0].equalsIgnoreCase("create")
							|| args[0].equalsIgnoreCase("new")) {
						p.sendMessage(ChatColor.RED
								+ "You need to specify a name for your empire!");
					} else if (args[0].equalsIgnoreCase("invite")) {
						p.sendMessage(ChatColor.RED + "You need to specify the player!");
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("create")
							|| args[0].equalsIgnoreCase("new")) {
						if (args[1].length() > 10) {
							p.sendMessage(ChatColor.RED
									+ "Empire names cannot be more than 10 characters.");
						} else {
							String n = args[1];
							createEmpire(p, n);
						}
					} else if (args[0].equalsIgnoreCase("invite")) {
						Player t = Bukkit.getServer().getPlayer(args[1]);
						String e = main.players.getString(p.getName() + ".Empire");
						if (e == null) {
							p.sendMessage("You are not part of an empire!");
						} else {
							String l = main.empires.getString(e + ".Leader");
							if (p.getName() == l) {
								invite(p, t, e);
							} else {
								p.sendMessage(ChatColor.RED + "You are not the leader of the empire!");
								p.sendMessage(ChatColor.RED + "Contact your empire leader to invite people!");
							}
						}
					}
				}
			}
		}

		return false;
	}

	public void createEmpire(Player p, String n) {
		main.empires.set(n + ".Leader", p.getName());
		main.players.set(p.getName() + ".Empire", n);
		p.sendMessage(ChatColor.BLUE + "You have created a new empire called "
				+ n);
	}

	public void invite(Player p, Player i, String n) {
		invites.put(i.getName(), n);
		p.sendMessage(ChatColor.BLUE + "You have invited " + i.getName() + " to your empire!");
		i.sendMessage(ChatColor.BLUE + "You have been invited to " + ChatColor.YELLOW + n);
		i.sendMessage(ChatColor.BLUE + "Type " + ChatColor.YELLOW + "/e join " + n + ChatColor.BLUE + " to join the empire!");
	}

}
