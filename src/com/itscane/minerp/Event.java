package com.itscane.minerp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {
	
	public double version = 0.1;
	
	public Main main;
	public Event(Main main) {
		this.main = main;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!main.players.contains(p.getName())) {
			double a = main.config.getDouble("StartingMoney");
			p.sendMessage(ChatColor.BLUE + "Creating wallet and bank accounts!");
			main.players.set(p.getName() + ".Wallet", a);
			main.players.set(p.getName() + ".Bank", 0);
			p.sendMessage(ChatColor.BLUE + "Account Creation success!");
			p.sendMessage(ChatColor.BLUE + "Welcome to " + Bukkit.getServer().getName() + "!");
			p.sendMessage(ChatColor.BLUE + "This server is running MineRP " + version + " made by ItsCane and Mark_Laymon!");
			main.save();
		} else {
			p.sendMessage(ChatColor.BLUE + "Welcome back to " + Bukkit.getServer().getName() + "!");
			p.sendMessage(ChatColor.BLUE + "This server is running MineRP " + version + " made by ItsCane and Mark_Laymon!");
		}
	}
	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		Block b = e.getBlockPlaced();
		Player p = e.getPlayer();
		int x = b.getLocation().getChunk().getX();
		int z = b.getLocation().getChunk().getZ();
		if (main.land.contains(x + "." + z)) {
			String en = main.land.getString(x + "." + z);
			if(main.empires.contains(en + ".Memebers." + p.getName())) {
				return;
			} else {
				p.sendMessage(ChatColor.RED + "You cannot build in the territory of " + en);
			}
		} else {
			return;
		}
	}
	@EventHandler
	
	public void chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (main.empires.contains(p.getName())) {
			String en = main.players.getString(p.getName() + ".Empire");
			e.setFormat(ChatColor.BLUE + "[" + ChatColor.YELLOW + en + ChatColor.BLUE + "]" + p.getName() + ":" + ChatColor.GREEN + e.getMessage());
		} else {
			return;
		}
	}

}
