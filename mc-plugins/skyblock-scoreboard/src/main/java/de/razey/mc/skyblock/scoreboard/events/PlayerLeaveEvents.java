package de.razey.mc.skyblock.scoreboard.events;

import de.razey.mc.skyblock.scoreboard.scoreboard.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Score;

public class PlayerLeaveEvents implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String teamName = ScoreboardUtils.playerNameToTeam(e.getPlayer());
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName).unregister();
    }

}
