package me.mc_cloud.gapcooldown.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionType;

import me.mc_cloud.gapcooldown.Main;

public class OnDeath implements Listener {
	
	public Main plugin;
	
	public OnDeath(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEat(PlayerDeathEvent e) {
		for (Material food : Main.itemCooldowns.keySet()) {
			if (Main.playerCooldowns.get(food).containsKey(e.getEntity().getUniqueId().toString())) {
				Main.playerCooldowns.get(food).remove(e.getEntity().getUniqueId().toString());
			}
		}
		for (PotionType type : Main.potionCooldowns.keySet()) {
			if (Main.playerPotionCooldowns.get(type).containsKey(e.getEntity().getUniqueId().toString())) {
				Main.playerPotionCooldowns.get(type).remove(e.getEntity().getUniqueId().toString());
			}
		}
	}
}