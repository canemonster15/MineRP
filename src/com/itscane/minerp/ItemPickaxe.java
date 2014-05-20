package com.itscane.minerp;

import com.itscane.minerp.Main;

public class ItemPickaxe {
	
	public Main main;
	public ItemPickaxe(Main main) {
		this.main = main;
	}
	
	public String getClassNeeded() {
		return "Miner";
	}
}
