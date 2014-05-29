package com.itscane.minerp;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassSerf implements CommandExecutor {
	
	public HashSet<String> serfs = new HashSet<String>();
	private static final String title = "serf";
	
	public Main main;
	public ClassSerf(Main main) {
		this.main = main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("serf")) {
			if(!(sender instanceof Player)) {
				System.out.println("Player expected!");
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission("minerp.serf")) {
					p.sendMessage(ChatColor.RED + "Invalid Permission!");
				} else {
					if (main.players.getString(p.getName() + ".Empire").equalsIgnoreCase(null)) {
						p.sendMessage(ChatColor.RED + "You are not part of an empire!");
					} else {
						String e = main.players.getString(p.getName() + ".Empire");
						ArrayList<String> l = (ArrayList<String>) main.empires.getStringList(e + ".Serfs");
						l.add(p.getName());
						main.empires.set(e + ".Serfs", l);
						main.save();
						main.players.set(p.getName() + ".Job", title);
						p.sendMessage(ChatColor.BLUE + "You have no become a serf!");
					}
				}
			}
		}
		
		return false;
	}
}
