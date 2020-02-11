package de.razey.mc.skyblock.scoreboard.scoreboard;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class BossBarUtils {

    public static HashMap<Player, BossBar> playerBossBars = new HashMap<>();

    public static HashMap<Player, Integer> xpNeededForLevelUp = new HashMap<>();

    public static HashMap<Player, Integer> currentXp = new HashMap<>();

    public static void removeBossBarForPlayer(Player p) {
        playerBossBars.get(p).removeAll();
        playerBossBars.remove(p);
        xpNeededForLevelUp.remove(p);
        currentXp.remove(p);
    }

    public static void increasePlayer(Player p) {
        currentXp.put(p, currentXp.get(p) + 1);
        System.out.println(currentXp.get(p));
        System.out.println(xpNeededForLevelUp.get(p));
        playerBossBars.get(p).setProgress((float) currentXp.get(p) / (float) xpNeededForLevelUp.get(p));
    }

    public static void levelUp(Player p) {
        int playerId = CoreApi.getInstance().getPlayerIdFromUuid(p.getUniqueId().toString());
        int playerLevel = CoreApi.getInstance().getSkyblockLevelFromPlayerId(playerId);

        playerBossBars.get(p).setTitle(
                ChatColor.translateAlternateColorCodes('&',"&7Level &a" + playerLevel));

        int needed = CoreApi.getInstance().getSkyblockXpNeededForLevelUp(playerLevel);
        int current = CoreApi.getInstance().getSkyblockXpFromPlayerId(playerId);

        playerBossBars.get(p).setProgress((float) current / (float) needed);

        currentXp.put(p, current);
        xpNeededForLevelUp.put(p, CoreApi.getInstance().getSkyblockXpNeededForLevelUp(playerLevel));
    }

    public static void createBossBarForPlayer(Player p) {
        int playerId = CoreApi.getInstance().getPlayerIdFromUuid(p.getUniqueId().toString());

        int playerLevel = CoreApi.getInstance().getSkyblockLevelFromPlayerId(playerId);

        BossBar bar = Bukkit.createBossBar(
                ChatColor.translateAlternateColorCodes('&',"&7Level &a" + playerLevel),
                BarColor.BLUE,
                BarStyle.SEGMENTED_20);

        playerBossBars.put(p, bar);

        int needed = CoreApi.getInstance().getSkyblockXpNeededForLevelUp(playerLevel);
        int current = CoreApi.getInstance().getSkyblockXpFromPlayerId(playerId);

        currentXp.put(p, current);
        xpNeededForLevelUp.put(p, CoreApi.getInstance().getSkyblockXpNeededForLevelUp(playerLevel));

        bar.setProgress((float)  current / (float) needed);

        bar.addPlayer(p);
    }

}
