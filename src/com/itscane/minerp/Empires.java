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
				if (p.hasPermission("minerp.empire")) {
					if (args.length == 0) {
						// Add help page here
					} else if (args.length == 1) {
						// Creating a Empire
						if (args[0].equalsIgnoreCase("create")
								|| args[0].equalsIgnoreCase("new")) {
							p.sendMessage(ChatColor.RED
									+ "You need to specify a name for your empire!");
						} else if (args[0].equalsIgnoreCase("invite")) {
							p.sendMessage(ChatColor.RED
									+ "You need to specify the player!");
						} else if (args[0].equalsIgnoreCase("join")) {
							p.sendMessage(ChatColor.RED
									+ "You need to specify the empire!");
						} else if (args[0].equalsIgnoreCase("kick")) {
							p.sendMessage(ChatColor.RED
									+ "You need to specify the player!");
						} else if (args[0].equalsIgnoreCase("claim")) {
							String pn = p.getName();
							String e = main.players.getString(pn + ".Empire");
							if (e == null) {
								p.sendMessage(ChatColor.RED
										+ "You are not part of an empire!");
							} else {
								String el = main.empires.getString(e
										+ ".Leader");
								if (pn == el) {
									claim(p, e);
								} else {
									p.sendMessage(ChatColor.RED
											+ "You are not the leader of your empire!");
								}
							}
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
							if (t == null) {
								p.sendMessage(ChatColor.RED + "Could not find player " + ChatColor.YELLOW + args[1]);
							} else {
								String e = main.players.getString(p.getName() + ".Empire");
								if (e.equalsIgnoreCase(null)) {
									p.sendMessage(ChatColor.RED + "You are not part of an empire!");
								} else {
									String l = main.empires.getString(e + ".Leader");
									if(!p.getName().equalsIgnoreCase(l)) {
										p.sendMessage(ChatColor.RED + "You are not the leader of your empire!");
									} else {
										invite(p, t , e);
									}
								}
							}
						} else if (args[0].equalsIgnoreCase("join")) {
							if (!invites.containsKey(p.getName())) {
								p.sendMessage(ChatColor.RED
										+ "You have not been invited to any empires!");
							} else {
								String e = invites.get(p.getName());
								if (e.equalsIgnoreCase(args[1])) {
									accept(p, e);
								} else {
									p.sendMessage(ChatColor.RED
											+ "You have not been invited to "
											+ ChatColor.YELLOW + e);
								}
							}
						} else if (args[0].equalsIgnoreCase("kick")) {
							if (main.players.getString(p.getName() + ".Empire") == null) {
								p.sendMessage(ChatColor.RED
										+ "You are not part of an empire!");
							} else {
								String e = main.players.getString(p.getName()
										+ ".Empire");
								String on = main.empires.getString(e
										+ ".Leader");
								if (!(on.equalsIgnoreCase(p.getName()))) {
									p.sendMessage(ChatColor.RED
											+ "You are not the leader of your empire!");
								} else {
									String tn = args[1];
									Player t = Bukkit.getServer().getPlayer(tn);
									if (t == null) {
										p.sendMessage(ChatColor.RED
												+ "Could not find player " + tn);
									} else {
										if (main.empires.contains(e
												+ ".Members." + tn)) {
											kickPlayer(t, e, p);
										} else {
											p.sendMessage(ChatColor.RED
													+ "That player isn't in your empire!");
										}
									}
								}
							}
						}
					} else {
						p.sendMessage(ChatColor.RED + "Too many args!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Invalid Permissions!");
				}
			}
		}

		return false;
	}

	public void createEmpire(Player p, String n) {
		main.empires.set(n + ".Leader", p.getName());
		main.empires.set(n + ".Members", "");
		main.players.set(p.getName() + ".Empire", n);
		p.sendMessage(ChatColor.BLUE + "You have created a new empire called "
				+ n);
		main.save();
	}
	
	public void invite(Player p, Player t, String e) {
		invites.put(t.getName(), e);
		p.sendMessage(ChatColor.BLUE + "You have invited " + t.getName()
				+ " to your empire!");
		t.sendMessage(ChatColor.BLUE + "You have been invited to "
				+ ChatColor.YELLOW + e);
		t.sendMessage(ChatColor.BLUE + "Type " + ChatColor.YELLOW + "/e join "
				+ e + ChatColor.BLUE + " to join the empire!");
		main.save();
	}
	
	public void kickPlayer(Player p, String e, Player o) {
		main.players.set(p.getName() + ".Empire", null);
		p.sendMessage(ChatColor.RED + "You were kicked from the empire "
				+ ChatColor.YELLOW + e);
		main.empires.set(e + ".Members." + p.getName(), null);
		o.sendMessage(ChatColor.BLUE + "You kicked " + ChatColor.YELLOW
				+ p.getName() + ChatColor.BLUE + " from your empire!");
		main.save();
	}

	public void accept(Player p, String e) {
		main.empires.set(e + ".Members", p.getName());
		p.sendMessage(ChatColor.BLUE + "You have joined " + ChatColor.YELLOW
				+ e);
		String on = main.empires.getString(e + ".Leader");
		main.players.set(p.getName() + ".Empire", e);
		Player o = Bukkit.getServer().getPlayer(on);
		o.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.BLUE
				+ " joined your empire!");
		invites.remove(p);
		main.save();
	}

	public void claim(Player p, String e) {
		int x = p.getLocation().getChunk().getX();
		int z = p.getLocation().getChunk().getZ();
		if (!main.land.contains(x + "")) {
			main.land.set(x + "", z + "." + e);
		} else {
			if (!main.land.contains(z + "")) {
				main.land.set(x + "", z + "." + e);
				p.sendMessage(ChatColor.BLUE + "You have claimed this land!");
				main.save();
			} else {
				p.sendMessage(ChatColor.RED + "This land is already claimed!");
			}
		}
	}
}
