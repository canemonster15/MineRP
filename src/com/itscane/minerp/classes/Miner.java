package com.itscane.minerp.classes;

import java.util.HashSet;

import org.bukkit.entity.Player;

import com.itscane.minerp.Main;

public class Miner {
	
	public static HashSet<String> miners = new HashSet<String>();
	
	public Main main;
	public Miner(Main main) {
		this.main = main;
	}
	
	public int strength() {
		return 9;
	}
}
