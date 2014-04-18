package com.itscane.minerp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {
	
	public double version = 0.1;
	
	public Main main;
	public Event(Main main) {
		this.main = main;
	}
	
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!main.players.contains(p.getName())) {
			double a = main.config.getDouble("StartingMoney");
			main.players.set(p.getName(), a);
			p.sendMessage(ChatColor.BLUE + "Welcome back to " + Bukkit.getServer().getName());
			p.sendMessage(ChatColor.BLUE + "This server is running MineRP " + version + " made by ItsCane and Mark_Laymon");
		} else {
			return;
		}
	}

}
