package dev.reddin.whosafkpapermc.events;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * Created by jasper on 8/17/16.
 */
public class AFKStatusOffEvent extends WhosAFKEvent {
	public AFKStatusOffEvent(Component message, Player player) {
		super(message, player);
	}
}
