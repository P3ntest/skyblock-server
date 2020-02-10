package de.razey.mc.skyblock.scoreboard;

import de.razey.mc.skyblock.scoreboard.events.PlayerEnterEvents;
import de.razey.mc.skyblock.scoreboard.events.PlayerLeaveEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new PlayerEnterEvents(),this);
        this.getServer().getPluginManager().registerEvents(new PlayerLeaveEvents(),this);
    }

    public void onDisable() {
        List<Team> teams = new ArrayList<>();

        Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach((team -> teams.add(team)));

        teams.forEach((team -> team.unregister()));
    }

}
