package de.razey.mc.skyblock.scoreboard.events;

import de.razey.mc.skyblock.scoreboard.scoreboard.BossBarUtils;
import de.razey.mc.skyblock.scoreboard.scoreboard.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerLeaveEvents implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String teamName = ScoreboardUtils.playerNameToTeam(e.getPlayer());
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName).unregister();
        BossBarUtils.removeBossBarForPlayer(e.getPlayer());
        ScoreboardUtils.playerTeamHashMap.remove(e.getPlayer());
    }

}
