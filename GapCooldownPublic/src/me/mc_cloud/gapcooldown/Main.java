package me.mc_cloud.gapcooldown;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.mc_cloud.gapcooldown.listeners.EndEat;
import me.mc_cloud.gapcooldown.listeners.StartEat;

public class Main extends JavaPlugin {
	
	public static Map<String, Long> cooldowns = new HashMap<String, Long>();
	public static int COOLDOWN = 0;
	
	@Override
	public void onEnable() {
		FileConfiguration config = getConfig();
		config.addDefault("cooldownTime", 45);
		config.options().copyDefaults(true);
		saveConfig();
		
		int cooldown = config.getInt("cooldownTime");
		if (cooldown < 0) {
			cooldown = 45;
		} else {
			COOLDOWN = cooldown;
		}
		
		new StartEat(this);
		new EndEat(this);
	}


}
