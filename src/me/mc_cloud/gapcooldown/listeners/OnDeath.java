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
		String entityUuid = e.getEntity().getUniqueId().toString();
		for (Material food : plugin.itemCooldowns.keySet()) {
			plugin.playerCooldowns.get(food).remove(entityUuid);
		}
		for (PotionType type : plugin.potionCooldowns.keySet()) {
			plugin.playerPotionCooldowns.get(type).remove(entityUuid);
		}
	}
}
