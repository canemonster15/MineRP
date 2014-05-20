package com.itscane.minerp;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.itscane.minerp.Main;

public class ClassSerf {
	
	public static HashMap<String, String> serfs = new HashMap<String, String>();
	
	public Main main;
	public ClassSerf(Main main) {
		this.main = main;
	}
	
	public void add(Player p, String e) {
		String n = p.getName();
		serfs.put(n, e);
		p.sendMessage(ChatColor.BLUE + "You have become a miner for you empire!");
	}
}
