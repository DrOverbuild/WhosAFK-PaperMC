/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package dev.reddin.whosafkpapermc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jasper
 */
public class WhosAFKRunnable implements Runnable{
	WhosAFKPaperMC plugin;
	public WhosAFKRunnable(WhosAFKPaperMC plugin){
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if (plugin.getConfigManager().getAutoAFKEnabled()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!p.hasPermission("whosafk.autoafk")){
					return;
				}

				if (plugin.playerIsAFK(p)) {
					return;
				}

				if (plugin.getAfkTimes().containsKey(p)) {
					plugin.getAfkTimes().replace(p, plugin.getAfkTimes().get(p) + 1);
				} else {
					plugin.getAfkTimes().put(p, 0);
				}

				if (plugin.getAfkTimes().get(p) > plugin.getConfigManager().getAutoAFKTimeOut()) {
					plugin.toggleAFKStatus(p);
				}
			}
		}
	}
}
