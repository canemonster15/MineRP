package com.itscane.minerp;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassSerf implements CommandExecutor {
	
	public HashSet<String> serfs = new HashSet<String>();
	
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
					
				}
			}
		}
		
		return false;
	}

}
