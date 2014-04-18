package com.itscane.minerp;

import java.io.File;
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
	 */

	public final Logger log = Logger.getLogger("Minecraft");
	public File configFile;
	public File playersFile;
	public File empiresFile;
	public File landFile;
	public FileConfiguration config;
	public FileConfiguration players;
	public FileConfiguration empires;
	public FileConfiguration land;

	public void onEnable() {
		configFile = new File(this.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		playersFile = new File(this.getDataFolder(), "players.yml");
		players = YamlConfiguration.loadConfiguration(playersFile);
		empiresFile = new File(this.getDataFolder(), "empires.yml");
		empires = YamlConfiguration.loadConfiguration(empiresFile);
		landFile = new File(this.getDataFolder(), "land.yml");
		land = YamlConfiguration.loadConfiguration(landFile);
		firstRun();
		getServer().getPluginManager().registerEvents(new Event(this), this);
		getServer().getPluginCommand("empires").setExecutor(new Empires(this));
		getServer().getPluginCommand("pay").setExecutor(new MoneyCommands(this));
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
		if(!config.contains("")) {
			config.set("StartingMoney", 150);
		} else {
			return;
		}
	}

}
