package com.itscane.minerp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Class implements CommandExecutor {
	
	public Main main;
	public Class(Main main) {
		this.main = main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("class")) {
			//Finish Class help page
			sender.sendMessage("Place Holder");
		}
		
		return false;
	}

}
