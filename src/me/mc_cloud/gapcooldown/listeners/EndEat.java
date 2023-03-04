package me.mc_cloud.gapcooldown.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.mc_cloud.gapcooldown.Main;
import me.mc_cloud.gapcooldown.utils.Utils;


public class EndEat implements Listener {
	
	public Main plugin;
	
	public EndEat(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if (e.getPlayer().hasPermission("gapCooldown.ignore")) return;
		
		for (Material food : Main.itemCooldowns.keySet()) {
			if (e.getItem().getType() == food) {
				Main.playerCooldowns.get(food).put(e.getPlayer().getUniqueId().toString(), Utils.todayPlus(0, 0, 0, Main.itemCooldowns.get(food)));
			}
		}
	}
}
