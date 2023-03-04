package me.mc_cloud.gapcooldown;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.mc_cloud.gapcooldown.listeners.EndEat;
import me.mc_cloud.gapcooldown.listeners.StartEat;
import me.mc_cloud.gapcooldown.utils.UpdateChecker;

public class Main extends JavaPlugin {
	
	public static Map<String, Long> enchantedGoldenAppleCooldowns = new HashMap<String, Long>();
	public static int ENCHANTED_GOLDEN_APPLE_COOLDOWN = 0;
	public static Map<String, Long> goldenAppleCooldowns = new HashMap<String, Long>();
	public static int GOLDEN_APPLE_COOLDOWN = 0;
	
	@Override
	public void onEnable() {
		FileConfiguration config = getConfig();
		
		config.set("cooldownTime", null); // Remove old config
		
		config.addDefault("enchanted_golden_apple.enabled", true);
		config.addDefault("enchanted_golden_apple.cooldown", 45);
		
		config.addDefault("golden_apple.enabled", true);
		config.addDefault("golden_apple.cooldown", 20);
		config.options().copyDefaults(true);
		saveConfig();
		
		boolean enabled = config.getBoolean("enchanted_golden_apple.enabled");
		int cooldown = config.getInt("enchanted_golden_apple.cooldown");
		if (enabled) {
			if (cooldown < 0) {
				cooldown = 45;
			} else {
				ENCHANTED_GOLDEN_APPLE_COOLDOWN = cooldown;
			}
		} else {
			ENCHANTED_GOLDEN_APPLE_COOLDOWN = -1;
		}
		
		enabled = config.getBoolean("golden_apple.enabled");
		cooldown = config.getInt("golden_apple.cooldown");
		if (enabled) {
			if (cooldown < 0) {
				cooldown = 45;
			} else {
				GOLDEN_APPLE_COOLDOWN = cooldown;
			}
		} else {
			GOLDEN_APPLE_COOLDOWN = -1;
		}

		
		new StartEat(this);
		new EndEat(this);
		
		new UpdateChecker(this, 101358).getVersion(version -> {
            if (!this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is a new update available (" + version + ")! Go to https://www.spigotmc.org/resources/gap-cooldown.101358/ to download the new version.");
            }
        });
	}


}
