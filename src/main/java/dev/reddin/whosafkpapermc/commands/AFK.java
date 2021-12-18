/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package dev.reddin.whosafkpapermc.commands;

import dev.reddin.whosafkpapermc.WhosAFKPaperMC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author jasper
 */
public class AFK implements CommandExecutor {

    WhosAFKPaperMC plugin;

    public AFK(WhosAFKPaperMC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

        Player p;

        if (args.length > 0 && sender.isOp()) {
            p = plugin.getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }
            plugin.toggleAFKStatus(p);
            return true;
        }

        if (sender instanceof Player) {
            p = (Player) sender;
        } else {
            sender.sendMessage("You must be a player!");
            return true;
        }

        plugin.toggleAFKStatus(p);
        return true;

    }

}
