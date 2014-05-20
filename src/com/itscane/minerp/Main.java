package com.itscane.minerp;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	/*TO-DO List
	 * -finish empires help page
	 * -finish moneycommands help page
	 * -finish RP help page
	 * -finish items they can sell page
	 * -make the on join message nicer
	 */
	
	/*DEVELOPMENT ORDER
	 * -finish item sales
	 * -add buying
	 * -independent master smiths
	 * -rest of classes
	 */

	public final Logger log = Logger.getLogger("Minecraft");
	public File configFile;
	public File playersFile;
	public File empiresFile;
	public File landFile;
	public File blocksFile;
	public FileConfiguration config;
	public FileConfiguration players;
	public FileConfiguration empires;
	public FileConfiguration land;
	public FileConfiguration blocks;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Event(this), this);
		configFile = new File(this.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		playersFile = new File(this.getDataFolder(), "players.yml");
		players = YamlConfiguration.loadConfiguration(playersFile);
		empiresFile = new File(this.getDataFolder(), "empires.yml");
		empires = YamlConfiguration.loadConfiguration(empiresFile);
		landFile = new File(this.getDataFolder(), "land.yml");
		land = YamlConfiguration.loadConfiguration(landFile);
		firstRun();
		save();
		getServer().getPluginCommand("empires").setExecutor(new Empires(this));
		getServer().getPluginCommand("money").setExecutor(new MoneyCommands(this));
		getServer().getPluginCommand("class").setExecutor(new RP(this));
		getServer().getPluginCommand("sell").setExecutor(new Sell(this));
		PluginDescriptionFile pdFile = this.getDescription();
		log.info(pdFile.getName() + " v" + pdFile.getVersion()
				+ " has been enabled!");
	}

	public void onDisable() {
		PluginDescriptionFile pdFile = this.getDescription();
		log.info(pdFile.getName() + " v" + pdFile.getVersion()
				+ " has been disabled!");
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		return false;
	}
	
	public void firstRun() {
		if(!config.contains("StartingMoney")) {
			config.set("StartingMoney", 150);
		} else {
			return;
		}
		if (blocks.contains("stone.id")) {
			return;
		} else {
			blocks.set("stone.id", 1);
			blocks.set("stone.price", 3);
			blocks.set("dirt.id", 3);
			blocks.set("dirt.price", 0.1);
			blocks.set("cobble.id", 4);
			blocks.set("cobble.price", 0.2);
			blocks.set("wood.id", 5);
			blocks.set("wood.price", 0.1);
			blocks.set("sappling.id", 6);
			blocks.set("sappling.price", 0.05);
			blocks.set("sand.id", 12);
			blocks.set("sand.price", 0.1);
			blocks.set("gravel.id", 13);
			blocks.set("gravel.price", 0.1);
		}
	}
	
	public void save() {
		try {
			config.save(configFile);
			players.save(playersFile);
			empires.save(empiresFile);
			land.save(landFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
