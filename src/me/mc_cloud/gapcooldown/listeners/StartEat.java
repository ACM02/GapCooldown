package me.mc_cloud.gapcooldown.listeners;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import me.mc_cloud.gapcooldown.Main;
import me.mc_cloud.gapcooldown.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


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
			
			for (Material food : Main.itemCooldowns.keySet()) {
				if (e.getPlayer().getItemInHand().getType() == food) {
					if (!Main.playerCooldowns.get(food).containsKey(e.getPlayer().getUniqueId().toString())) return;
					if (Main.playerCooldowns.get(food).get(e.getPlayer().getUniqueId().toString()) > new Date().getTime()) {
						e.setCancelled(true);
						if (Main.instance.getConfig().getBoolean("wait-messages.chat")) e.getPlayer().sendMessage(ChatColor.RED + "That item is on cooldown. Try again in " + Utils.secsUntil(Main.playerCooldowns.get(food).get(e.getPlayer().getUniqueId().toString())) + "s");
						if (Main.instance.getConfig().getBoolean("wait-messages.action-bar")) e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "That item is on cooldown. Try again in " + Utils.secsUntil(Main.playerCooldowns.get(food).get(e.getPlayer().getUniqueId().toString())) + "s"));
					}
				}
			}
			
			if (e.getPlayer().getItemInHand().getType() == Material.POTION) {
				PotionMeta meta = (PotionMeta) e.getPlayer().getItemInHand().getItemMeta();
				for (PotionType type : Main.potionCooldowns.keySet()) {
					if (meta.getBasePotionData().getType() == type) {
						if (!Main.playerPotionCooldowns.get(type).containsKey(e.getPlayer().getUniqueId().toString())) return;
						if (Main.playerPotionCooldowns.get(type).get(e.getPlayer().getUniqueId().toString()) > new Date().getTime()) {
							e.setCancelled(true);
							if (Main.instance.getConfig().getBoolean("wait-messages.chat")) e.getPlayer().sendMessage(ChatColor.RED + "That item is on cooldown. Try again in " + Utils.secsUntil(Main.playerPotionCooldowns.get(type).get(e.getPlayer().getUniqueId().toString())) + "s");
							if (Main.instance.getConfig().getBoolean("wait-messages.action-bar")) e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "That item is on cooldown. Try again in " + Utils.secsUntil(Main.playerPotionCooldowns.get(type).get(e.getPlayer().getUniqueId().toString())) + "s"));
						}
					}
				}
			}
		}
	}
}
