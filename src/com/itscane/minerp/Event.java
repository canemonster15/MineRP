package com.itscane.minerp;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {
	
	public Main main;
	public Event(Main main) {
		this.main = main;
	}
	
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!main.players.contains(p.getName())) {
			double a = main.config.getDouble("StartingMoney");
			main.players.set(p.getName(), a);
		} else {
			return;
		}
	}

}
