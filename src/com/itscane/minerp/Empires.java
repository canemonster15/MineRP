package com.itscane.minerp;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
						p.sendMessage(ChatColor.BLUE + "-----====" + ChatColor.GREEN + "MineRP" + ChatColor.BLUE + "====----");
						p.sendMessage(ChatColor.BLUE + "/e new <name> - " + ChatColor.GREEN + "Creates a new empire.");
						p.sendMessage(ChatColor.BLUE + "/e invite <name> - " + ChatColor.GREEN + "Invites player to empire!");
						p.sendMessage(ChatColor.BLUE + "/e join <name> - " + ChatColor.GREEN + "Joins a empire.");
						p.sendMessage(ChatColor.BLUE + "/e kick <name> - " + ChatColor.GREEN + "Kicks a player from your empire.");
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
							if (e.equalsIgnoreCase(null)) {
								p.sendMessage(ChatColor.RED
										+ "You are not part of an empire!");
							} else {
								String el = main.empires.getString(e
										+ ".Leader");
								if (pn.equalsIgnoreCase(el)) {
									claim(p, e);
								} else {
									p.sendMessage(ChatColor.RED
											+ "You are not the leader of your empire!");
								}
							}
						} else if (args[0].equalsIgnoreCase("disband")) {
							String n = p.getName();
							if(main.players.getString(n + ".Empire").equalsIgnoreCase("")) {
								p.sendMessage(ChatColor.RED + "You are not part of an empire!");
							} else {
								String e = main.empires.getString(n + ".Empire");
								String el = main.empires.getString(e + ".Leader");
								if (n.equalsIgnoreCase(el)) {
									disband(p, e);
								} else {
									p.sendMessage(ChatColor.RED + "You are not the leader of your empire!");
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
								p.sendMessage(ChatColor.RED
										+ "Could not find player "
										+ ChatColor.YELLOW + args[1]);
							} else {
								String e = main.players.getString(p.getName()
										+ ".Empire");
								if (e.equalsIgnoreCase(null)) {
									p.sendMessage(ChatColor.RED
											+ "You are not part of an empire!");
								} else {
									String l = main.empires.getString(e
											+ ".Leader");
									if (!p.getName().equalsIgnoreCase(l)) {
										p.sendMessage(ChatColor.RED
												+ "You are not the leader of your empire!");
									} else {
										ArrayList<String> ml = (ArrayList<String>) main.empires
												.getStringList(e + ".Members");
										if (ml.contains(t.getName())) {
											p.sendMessage(ChatColor.RED
													+ "That player is already in the empire!");
										} else {
											invite(p, t, e);
										}
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
							if (main.players.getString(p.getName() + ".Empire")
									.equalsIgnoreCase(null)) {
								p.sendMessage(ChatColor.RED
										+ "You are not part of an empire!");
							} else {
								String e = main.players.getString(p.getName()
										+ ".Empire");
								String on = main.empires.getString(e
										+ ".Leader");
								if (!on.equalsIgnoreCase(p.getName())) {
									p.sendMessage(ChatColor.RED
											+ "You are not the leader of your empire!");
								} else {
									String tn = args[1];
									Player t = Bukkit.getServer().getPlayer(tn);
									if (t == null) {
										p.sendMessage(ChatColor.RED
												+ "Could not find player " + tn);
									} else {
										if (main.empires.getStringList(
												e + ".Members").contains(tn)) {
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
		createFlag(p);
	}

	public void invite(Player p, Player t, String e) {
		invites.put(t.getName(), e);
		p.sendMessage(ChatColor.BLUE + "You have invited " + ChatColor.YELLOW
				+ t.getName() + ChatColor.BLUE + " to your empire!");
		t.sendMessage(ChatColor.BLUE + "You have been invited to "
				+ ChatColor.YELLOW + e);
		t.sendMessage(ChatColor.BLUE + "Type " + ChatColor.YELLOW + "/e join "
				+ e + ChatColor.BLUE + " to join the empire!");
		main.save();
	}

	public void kickPlayer(Player p, String e, Player o) {
		ArrayList<String> l = (ArrayList<String>) main.empires.getStringList(e
				+ ".Memebers");
		l.remove(p.getName());
		main.empires.set(e + ".Memebers", l);
		main.players.set(p.getName() + ".Empire", null);
		p.sendMessage(ChatColor.RED + "You were kicked from the empire "
				+ ChatColor.YELLOW + e);
		main.empires.getStringList(e + ".Members").remove(p.getName());
		o.sendMessage(ChatColor.BLUE + "You kicked " + ChatColor.YELLOW
				+ p.getName() + ChatColor.BLUE + " from your empire!");
		main.save();
	}

	public void accept(Player p, String e) {
		ArrayList<String> l = (ArrayList<String>) main.empires.getStringList(e
				+ ".Members");
		l.add(p.getName());
		main.empires.set(e + ".Members", l);
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
		if (!main.land.contains("Land." + x)) {
			main.land.set("Land." + x + ".", z + "." + e);
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
	
	public void createFlag(Player p) {
		for (double i = p.getLocation().getY(); i > i + 7; i++) {
			double x = p.getLocation().getX();
			double z = p.getLocation().getZ();
			World w = p.getLocation().getWorld();
			Location l = new Location(w, x, i, z);
			Block b = l.getBlock();
			b.setType(Material.FENCE);
		}
	}
	
	public void disband(Player p, String e) {
		main.players.set(p.getName() + ".Empire", "");
		main.empires.set(e, null);
		Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "The empire of " + ChatColor.YELLOW + e + ChatColor.BLUE + " has been disbanded!");
		ArrayList<String> m = (ArrayList<String>) main.empires.get(e + ".Members");
		int s = m.size();
		for (int i = 0; i > s; i++) {
			String n = m.get(i);
			main.players.set(n + ".Empire", "");
		}
	}
}
