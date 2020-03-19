package de.razey.mc.skyblock.scoreboard.events;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.scoreboard.scoreboard.BossBarUtils;
import de.razey.mc.skyblock.scoreboard.scoreboard.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Scanner;

public class BlockActionEvents implements Listener {

    public void grantXp(Player player, int amount) {
        int check = CoreApi.getInstance().giveSkyblockXpToPlayerId(amount,
                CoreApi.getInstance().getPlayerIdFromUuid(player.getUniqueId().toString()),
                true);
        if (check == -1) {
            BossBarUtils.increasePlayer(player);
        } else {
            BossBarUtils.levelUp(player);
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&6Level Up!"),
                    ChatColor.translateAlternateColorCodes('&', "&7Du bist jetzt Level &a" + check),
                    5, 40, 15);
            ScoreboardUtils.playerTeamHashMap.get(player)
                    .setSuffix(ScoreboardUtils.getSuffix(player));
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 8, 1);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.isCancelled())
            grantXp(e.getPlayer(), 1);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!e.isCancelled())
            grantXp(e.getPlayer(), 1);
    }


}
