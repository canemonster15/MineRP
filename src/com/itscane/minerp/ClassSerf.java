package com.itscane.minerp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClassSerf implements CommandExecutor {
	
	public Main main;
	public ClassSerf(Main main) {
		this.main = main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("serf")) {
			sender.sendMessage("Place holder.");
		}
		
		return false;
	}

}