package com.itscane.minerp.classes;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.itscane.minerp.Main;

public class Miner {
	
	public static HashSet<String> miners = new HashSet<String>();
	
	public Main main;
	public Miner(Main main) {
		this.main = main;
	}
	
	public int getStrength() {
		return 9;
	}
	
	public void add(Player p) {
		String n = p.getName();
		miners.add(n);
		p.sendMessage(ChatColor.BLUE + "You have become a miner!");
	}
	
}
