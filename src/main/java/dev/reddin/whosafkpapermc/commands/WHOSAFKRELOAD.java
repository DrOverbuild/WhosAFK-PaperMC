package dev.reddin.whosafkpapermc.commands;

import dev.reddin.whosafkpapermc.WhosAFKPaperMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by jasper on 11/30/16.
 */
public class WHOSAFKRELOAD implements CommandExecutor {
	WhosAFKPaperMC plugin;

	public WHOSAFKRELOAD(WhosAFKPaperMC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		plugin.getConfigManager().loadConfig();
		sender.sendMessage("Reloaded the WhosAFK config.");
		return true;
	}
}
