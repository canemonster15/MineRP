package com.itscane.minerp.classes;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.itscane.minerp.Main;

public class Serf {
	
	public static HashSet<String> serfs = new HashSet<String>();
	
	public Main main;
	public Serf(Main main) {
		this.main = main;
	}
	
	public void add(Player p) {
		String n = p.getName();
		serfs.add(n);
		p.sendMessage(ChatColor.BLUE + "You have become a miner!");
	}
}
