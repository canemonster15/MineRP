package com.itscane.minerp.items;

import com.itscane.minerp.Main;

public class Pickaxe {
	
	public Main main;
	public Pickaxe(Main main) {
		this.main = main;
	}
	
	public String getClassNeeded() {
		return "Miner";
	}
}
