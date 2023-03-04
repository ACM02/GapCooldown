package me.mc_cloud.gapcooldown.listeners;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.mc_cloud.gapcooldown.Main;
import me.mc_cloud.gapcooldown.utils.Utils;
import net.md_5.bungee.api.ChatColor;


public class StartEat implements Listener {
	
	public Main plugin;
	
	public StartEat(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEat(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("gapCooldown.ignore")) return;
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getPlayer().getItemInHand().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
				if (!Main.enchantedGoldenAppleCooldowns.containsKey(e.getPlayer().getUniqueId().toString())) return;
				if (Main.enchantedGoldenAppleCooldowns.get(e.getPlayer().getUniqueId().toString()) > new Date().getTime()) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "That item is on cooldown. Try again in " + Utils.secsUntil(Main.enchantedGoldenAppleCooldowns.get(e.getPlayer().getUniqueId().toString())) + "s");
				}
			} else if (e.getPlayer().getItemInHand().getType() == Material.GOLDEN_APPLE) {
				if (!Main.goldenAppleCooldowns.containsKey(e.getPlayer().getUniqueId().toString())) return;
				if (Main.goldenAppleCooldowns.get(e.getPlayer().getUniqueId().toString()) > new Date().getTime()) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "That item is on cooldown. Try again in " + Utils.secsUntil(Main.goldenAppleCooldowns.get(e.getPlayer().getUniqueId().toString())) + "s");
				}
			}
		}
	}
}
