package me.mc_cloud.gapcooldown.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.mc_cloud.gapcooldown.Main;

public class OnDeath implements Listener {
	
	public Main plugin;
	
	public OnDeath(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEat(PlayerDeathEvent e) {
		if (Main.enchantedGoldenAppleCooldowns.containsKey(e.getEntity().getUniqueId().toString())) {
			Main.enchantedGoldenAppleCooldowns.remove(e.getEntity().getUniqueId().toString());
		}
		if (Main.goldenAppleCooldowns.containsKey(e.getEntity().getUniqueId().toString())) {
			Main.goldenAppleCooldowns.remove(e.getEntity().getUniqueId().toString());
		}
	}
}