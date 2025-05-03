package me.mc_cloud.gapcooldown.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import me.mc_cloud.gapcooldown.Main;
import me.mc_cloud.gapcooldown.utils.Utils;


public class EndEat implements Listener {
	
	private Main plugin;
	
	public EndEat(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		if (player.hasPermission("gapCooldown.ignore")) return;
		String playerUuid = player.getUniqueId().toString();

		Material eatedItem = e.getItem().getType();
		if (plugin.itemCooldowns.keySet().contains(eatedItem)) {
			Long nextEatAllowed = Utils.todayPlus(0, 0, 0, plugin.itemCooldowns.get(eatedItem));
			plugin.playerCooldowns.get(eatedItem).put(playerUuid, nextEatAllowed);
		}

		if (e.getItem().hasItemMeta() && e.getItem().getItemMeta() instanceof PotionMeta) {
			PotionMeta potionMeta = (PotionMeta) e.getItem().getItemMeta();
			PotionType potionType = potionMeta.getBasePotionData().getType();
			
			if (plugin.potionCooldowns.keySet().contains(potionType)) {
				Long nextDrinkAllowed = Utils.todayPlus(0, 0, 0, plugin.potionCooldowns.get(potionType));
				plugin.playerPotionCooldowns.get(potionType).put(playerUuid, nextDrinkAllowed);
			}
		}
	}
}
