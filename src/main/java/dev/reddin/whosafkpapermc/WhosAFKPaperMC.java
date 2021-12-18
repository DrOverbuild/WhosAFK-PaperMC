package dev.reddin.whosafkpapermc;

import dev.reddin.whosafkpapermc.commands.AFK;
import dev.reddin.whosafkpapermc.commands.WHOSAFKRELOAD;
import dev.reddin.whosafkpapermc.events.AFKStatusOffEvent;
import dev.reddin.whosafkpapermc.events.AFKStatusOnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class WhosAFKPaperMC extends JavaPlugin {
    public static WhosAFKPaperMC instance;

    WhosAFKEventHandler handler;
    AFK afkCommand;
    ConfigManager configManager;

    Map<Player, Integer> afkTimes = new HashMap<>();

    @Override
    public void onEnable() {
        handler = new WhosAFKEventHandler(this);
        afkCommand = new AFK(this);
        configManager = new ConfigManager(this);

		getServer().getPluginManager().registerEvents(handler, this);
        getCommand("afk").setExecutor(afkCommand);
        getCommand("whosafkreload").setExecutor(new WHOSAFKRELOAD(this));

        Team team = getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");
        if (team == null) {
            team = getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("afkers");
        }

        team.color(NamedTextColor.BLUE);
        team.suffix(Component.text(" (AFK)"));

        configManager.loadConfig();

		getServer().getScheduler().runTaskTimer(this, new WhosAFKRunnable(this), 20, 20);

        instance = this;
    }

    @Override
    public void onDisable() {
        saveConfig();

        afkTimes.clear();

        Team team = getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");

        if (team == null) return;

        team.unregister();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Map<Player, Integer> getAfkTimes() {
        return afkTimes;
    }

    public void toggleAFKStatus(Player p){
        if(playerIsAFK(p)){
            removeAFKStatus(p);
        }else{
            addAFKStatus(p);
        }
    }

    public void removeAFKStatus(@NotNull Player p){
        TextComponent comp = Component.text("* ")
                .append(Component.text(p.getName() + " is no longer AFK.", NamedTextColor.BLUE));
        AFKStatusOffEvent event = new AFKStatusOffEvent(comp, p);
        getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
        if (event.getPlayer() == null) return;

        Team team = getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");

        if (team == null) return;

        team.removeEntry(p.getName());

        if(event.getMessage() != null){
            getServer().broadcast(event.getMessage());
        }
    }

    public void addAFKStatus(@NotNull Player p){
        TextComponent comp = Component.text("* ")
                .append(Component.text(p.getName() + " is now AFK.", NamedTextColor.BLUE));
        AFKStatusOnEvent event = new AFKStatusOnEvent(comp, p);
        getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
        if (event.getPlayer() == null) return;

        Team team = getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");

        if (team == null) return;

        team.addEntry(p.getName());

        if(event.getMessage() != null){
            getServer().broadcast(event.getMessage());
        }
    }

    public boolean playerIsAFK(@NotNull Player p){
        return playerIsAFK(p.getName());
    }

    public boolean playerIsAFK(String playerName) {
        Team team = getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");
        if (team == null) return false;
        return team.hasEntry(playerName);
    }

    public @Nullable Player getPlayer(String playerName){
        for(Player p:getServer().getOnlinePlayers()){
            if(p.getName().equals(playerName)){
                return p;
            }
        }
        return null;
    }
}
