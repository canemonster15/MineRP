package com.itscane.minerp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class Event implements Listener {

	public double version = 0.1;

	public Main main;
	public ClassSerf serf;

	public Event(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!main.players.contains(p.getName())) {
			double a = main.config.getDouble("StartingMoney");
			p.sendMessage(ChatColor.BLUE + "Creating wallet and bank accounts!");
			main.players.set(p.getName() + ".Wallet", a);
			main.players.set(p.getName() + ".Bank", 0);
			main.players.set(p.getName() + ".Empire", "");
			main.players.set(p.getName() + ".Job", "");
			main.save();
			p.sendMessage(ChatColor.BLUE + "Account Creation success!");
			p.sendMessage(ChatColor.BLUE + "Welcome to "
					+ Bukkit.getServer().getName() + "!");
			p.sendMessage(ChatColor.BLUE + "This server is running MineRP "
					+ version + " made by ItsCane and Mark_Laymon!");
			main.save();
		} else {
			p.sendMessage(ChatColor.BLUE + "Welcome back to "
					+ Bukkit.getServer().getName() + "!");
			p.sendMessage(ChatColor.BLUE + "This server is running MineRP "
					+ version + " made by ItsCane and Mark_Laymon!");
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
			if (main.empires.contains(en + ".Memebers." + p.getName())) {
				return;
			} else {
				p.sendMessage(ChatColor.RED
						+ "You cannot build in the territory of " + en);
			}
		} else {
			return;
		}
		
		if (serf.serfs.contains(p.getName())) {
			return;
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		int x = b.getLocation().getChunk().getX();
		int z = b.getLocation().getChunk().getZ();
		if (main.land.contains(x + "." + z)) {
			String en = main.land.getString(x + "." + z);
			if (main.empires.contains(en + ".Memebers." + p.getName())) {
				return;
			} else {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED
						+ "You cannot build in the territory of " + en);
			}
		} else {
			return;
		}
		toolCheck(p, e);
	}

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (main.empires.contains(p.getName())) {
			String en = main.players.getString(p.getName() + ".Empire");
			e.setFormat(ChatColor.BLUE + "[" + ChatColor.YELLOW + en
					+ ChatColor.BLUE + "]" + p.getName() + ":"
					+ ChatColor.GREEN + e.getMessage());
		} else {
			return;
		}
	}
	
	public void toolCheck(Player p, BlockBreakEvent e) {
		ItemStack dpick = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemStack daxe = new ItemStack(Material.DIAMOND_AXE);
		ItemStack dhoe = new ItemStack(Material.DIAMOND_HOE);
		ItemStack dshovel = new ItemStack(Material.DIAMOND_SPADE);
		ItemStack ipick = new ItemStack(Material.IRON_PICKAXE);
		ItemStack iaxe = new ItemStack(Material.IRON_AXE);
		ItemStack ihoe = new ItemStack(Material.IRON_HOE);
		ItemStack ishovel = new ItemStack(Material.IRON_SPADE);
		ItemStack gpick = new ItemStack(Material.GOLD_PICKAXE);
		ItemStack gaxe = new ItemStack(Material.GOLD_AXE);
		ItemStack ghoe = new ItemStack(Material.GOLD_HOE);
		ItemStack gshovel = new ItemStack(Material.GOLD_SPADE);
		ItemStack spick = new ItemStack(Material.STONE_PICKAXE);
		ItemStack saxe = new ItemStack(Material.STONE_AXE);
		ItemStack shoe = new ItemStack(Material.STONE_HOE);
		ItemStack sshovel = new ItemStack(Material.STONE_SPADE);
		ItemStack wpick = new ItemStack(Material.WOOD_PICKAXE);
		ItemStack waxe = new ItemStack(Material.WOOD_AXE);
		ItemStack whoe = new ItemStack(Material.WOOD_HOE);
		ItemStack wshovel = new ItemStack(Material.WOOD_SPADE);
		if (p.getInventory().getItemInHand() == dpick
				|| p.getInventory().getItemInHand() == daxe
				|| p.getInventory().getItemInHand() == dhoe
				|| p.getInventory().getItemInHand() == dshovel
				|| p.getInventory().getItemInHand() == ipick
				|| p.getInventory().getItemInHand() == iaxe
				|| p.getInventory().getItemInHand() == ihoe
				|| p.getInventory().getItemInHand() == ishovel
				|| p.getInventory().getItemInHand() == gpick
				|| p.getInventory().getItemInHand() == gaxe
				|| p.getInventory().getItemInHand() == ghoe
				|| p.getInventory().getItemInHand() == gshovel
				|| p.getInventory().getItemInHand() == spick
				|| p.getInventory().getItemInHand() == saxe
				|| p.getInventory().getItemInHand() == shoe
				|| p.getInventory().getItemInHand() == sshovel
				|| p.getInventory().getItemInHand() == wpick
				|| p.getInventory().getItemInHand() == waxe
				|| p.getInventory().getItemInHand() == whoe
				|| p.getInventory().getItemInHand() == wshovel) {
			String n = p.getName();
			
			if (serf.serfs.contains(n)) {
				return;
			} else {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You need to be a serf to use this tool!");
			}
		}
	}

}
