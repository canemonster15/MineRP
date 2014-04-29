package com.itscane.minerp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.itscane.minerp.classes.Serf;

public class RP implements CommandExecutor {
	
	public Serf serf;
	
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
							serf.add(p);
						}
					}
				}
			}
		}
		
		return false;
	}

}
