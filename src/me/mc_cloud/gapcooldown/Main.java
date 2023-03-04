package me.mc_cloud.gapcooldown;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;

import me.mc_cloud.gapcooldown.listeners.EndEat;
import me.mc_cloud.gapcooldown.listeners.StartEat;
import me.mc_cloud.gapcooldown.utils.UpdateChecker;

public class Main extends JavaPlugin {
	
	/*
	 * TODO
	 * 
	 * Ideas:
	 * Option to disable in creative
	 * Option to disable based on world
	 * Message config
	 */
	
	public static final String CONFIG_HEADER = "GapCooldown version 2.0\n"
			+ "";
	
	public static Map<Material, Map<String, Long>> playerCooldowns = new HashMap<>();
	public static Map<Material, Integer> itemCooldowns = new HashMap<>();
	public static Map<PotionType, Integer> potionCooldowns = new HashMap<>();
	public static Map<PotionType, Map<String, Long>> playerPotionCooldowns = new HashMap<>();
	//public static Map<String, Long> enchantedGoldenAppleCooldowns = new HashMap<String, Long>();
	//public static int ENCHANTED_GOLDEN_APPLE_COOLDOWN = 0;
	//public static Map<String, Long> goldenAppleCooldowns = new HashMap<String, Long>();
	//public static int GOLDEN_APPLE_COOLDOWN = 0;
	
	public static final Material[] FOODS = {Material.ENCHANTED_GOLDEN_APPLE, Material.GOLDEN_APPLE, Material.APPLE, Material.BAKED_POTATO,
			Material.BEETROOT, Material.BEETROOT_SOUP, Material.BREAD, Material.CARROT, Material.CHORUS_FRUIT, Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_COD,
			Material.COOKED_MUTTON, Material.COOKED_PORKCHOP, Material.COOKED_RABBIT, Material.COOKED_SALMON, Material.COOKIE, Material.DRIED_KELP,
			Material.GLOW_BERRIES, Material.GOLDEN_CARROT, Material.HONEY_BOTTLE, Material.MELON_SLICE, Material.MUSHROOM_STEW, Material.POISONOUS_POTATO,
			Material.POTATO, Material.PUFFERFISH, Material.PUMPKIN_PIE, Material.RABBIT_STEW, Material.BEEF, Material.CHICKEN, Material.COD, Material.MUTTON,
			Material.PORKCHOP, Material.RABBIT, Material.SALMON, Material.ROTTEN_FLESH, Material.SPIDER_EYE, Material.SUSPICIOUS_STEW, Material.SWEET_BERRIES,
			Material.TROPICAL_FISH};
	
	public static final PotionType[] EFFECTS = PotionType.values();
	
	
	public static Main instance;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		FileConfiguration config = getConfig();
		
		config.set("cooldownTime", null); // Remove old config
		
		config.addDefault("wait-messages.chat", false);
		config.addDefault("wait-messages.action-bar", true);
		
		config.addDefault("enchanted_golden_apple.cooldown", 45);
		config.addDefault("golden_apple.cooldown", 20);
		config.options().copyDefaults(true);
		config.options().header(CONFIG_HEADER);
		saveConfig();
		
		
		for (Material material : FOODS) {
			int cooldown = config.getInt(material.toString().toLowerCase() + ".cooldown");
			if (cooldown != 0) {
				playerCooldowns.put(material, new HashMap<String, Long>());
				itemCooldowns.put(material, cooldown);
			}
		}
		
		for (PotionType potionType : EFFECTS) {
			int cooldown = config.getInt("potion." + potionType.toString().toLowerCase() + ".cooldown");
			if (cooldown != 0) {
				potionCooldowns.put(potionType, cooldown);
				playerPotionCooldowns.put(potionType, new HashMap<String, Long>());
			}
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
