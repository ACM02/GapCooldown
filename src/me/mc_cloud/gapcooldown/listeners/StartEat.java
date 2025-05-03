package me.mc_cloud.gapcooldown.listeners;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionType;

import me.mc_cloud.gapcooldown.Main;
import me.mc_cloud.gapcooldown.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class StartEat implements Listener {
	
	private Main plugin;
	
	public StartEat(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEat(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("gapCooldown.ignore")) return;
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			PlayerInventory playerInventory = e.getPlayer().getInventory();
			Material mainHandItem = playerInventory.getItemInMainHand();
			Material offHandItem = playerInventory.getItemInOffHand();
			if (areCooldownForThisFood(mainHandItem)) {
				cancelEventIfNeeded(e, getPlayerCooldown(plugin.playerCooldowns.get(mainHandItem), e.getPlayer())); 
			}
			if (areCooldownForThisFood(offHandItem)) {
				cancelEventIfNeeded(e, getPlayerCooldown(plugin.playerCooldowns.get(offHandItem), e.getPlayer())); 
			}
			if (areCooldownForThisPotion(mainHandItem)) {
				cancelEventIfNeeded(e, getPlayerCooldown(plugin.playerPotionCooldowns.get(mainHandItem), e.getPlayer());
			}
			if (areCooldownForThisPotion(offHandItem)) {
				cancelEventIfNeeded(e, getPlayerCooldown(plugin.playerPotionCooldowns.get(mainHandItem), e.getPlayer());
			}
		}
	}

	private boolean areCooldownForThisFood(ItemStack itemInHand) {
		return plugin.itemCooldowns.keySet().contains(itemInHand.getType());
	}

	private boolean areCooldownForThisPotion(ItemStack itemInHand) {
		if (itemInHand.getType().equals(Material.POTION)) {
			PotionMeta potionMeta = (PotionMeta) itemInHand.getItemMeta();
			return plugin.playerPotionCooldowns.keySet().contains(potionMeta.getBasePotionType());
		}
		return false;
	}
	
	private Long getPlayerCooldown(Map<String, Long> playerItemCooldowns, Player player) {
		String playerUuid = player.getUniqueId().toString();
		if (playerItemCooldowns.containsKey(playerUuid)) {
			Long lastEat = playerItemCooldowns.get(playerUuid);
			if (lastEat > new Date().getTime()) {
				return lastEat - new Date().getTime();
			}
		}
		return 0l;
	}

	private void cancelEventIfNeeded(PlayerInteractEvent event, Long cooldown) {
		if (cooldown > 0) {
			event.setCancelled(true);
			FileConfiguration config = plugin.getConfig();
			int cooldownSec = (int) (cooldown / 1000);
			if (config.getBoolean("wait-messages.chat")) {
				event.getPlayer().sendMessage(ChatColor.RED + "That item is on cooldown. Try again in " + cooldownSec + "s");
			}
			if (config.getBoolean("wait-messages.action-bar")) {
				event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
					TextComponent.fromLegacyText(ChatColor.RED + "That item is on cooldown. Try again in " + cooldownSec + "s"));	
			}
		}
	}
}
