package me.mc_cloud.gapcooldown.utils;

import java.util.Date;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

	public static double trunc(double d, int decimalsToKeep) {
		d *= Math.pow(10, decimalsToKeep);
		d = (int) d;
		d /= Math.pow(10, decimalsToKeep);
		return d;
	}
	
	public static long todayPlus(int days, int hours, int mins, int secs) {
		return new Date().getTime() + (days * 1000 * 60 * 60 * 24) + (hours * 1000 * 60 * 60) + (mins * 1000 * 60) + (secs * 1000);
	}
	
	public static int milisUntil(long date) {
		return (int) ((date - new Date().getTime()));
	}
	
	public static int secsUntil(long date) {
		return (int) ((date - new Date().getTime()) / 1000);
	}
	
	public static int minsUntil(long date) {
		return (int) ((date - new Date().getTime()) / 1000 / 60);
	}
	
	public static int hoursUntil(long date) {
		return (int) ((date - new Date().getTime()) / 1000 / 60 / 60);
	}
	
	public static int daysUntil(long date) {
		return (int) ((date - new Date().getTime()) / 1000 / 60 / 60 / 24);
	}
	
	/*
	 * @param chance the chance for the thing to occur (ex: 50 is recieved, the chance will be 1 in 50)
	 */
	public boolean rollTheDice(int chance) {
		Random r = new Random();
		int rand = r.nextInt(chance - 1);
		if (rand == 0) {
			return true;
		}
		return false;
	}
	
	public static int getOpenSlots(Player p) {
		int counter = 0;
		for (ItemStack i : p.getInventory()) {
			if (i == null) {
				counter++;
			}
		}
		return counter;
	}
}
