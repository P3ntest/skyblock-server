package de.razey.mc.skyblock.scoreboard.events;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.scoreboard.scoreboard.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

public class PlayerEnterEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String teamName = ScoreboardUtils.playerNameToTeam(e.getPlayer());
        Team t = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamName);
        int playerId = CoreApi.getInstance().getPlayerId(e.getPlayer().getUniqueId().toString());
        t.setSuffix(ChatColor.translateAlternateColorCodes(
                '&', " &7[&a" + CoreApi.getInstance().getSkyblockLevelFromPlayerId(playerId) + "&7]"));
        t.addEntry(e.getPlayer().getName());

        int displayRankId = CoreApi.getInstance().getHighestRankIdOfPlayer(playerId);

        t.setPrefix(ChatColor.translateAlternateColorCodes('&', CoreApi.getInstance().getRankPrefix(displayRankId)));
        t.setColor(ChatColor.valueOf(CoreApi.getInstance().getRankColor(displayRankId)));
    }

}
