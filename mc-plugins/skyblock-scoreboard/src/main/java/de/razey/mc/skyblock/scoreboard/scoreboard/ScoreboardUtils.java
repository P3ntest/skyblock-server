package de.razey.mc.skyblock.scoreboard.scoreboard;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.entity.Player;

public abstract class ScoreboardUtils {

    public static String playerNameToTeam(Player p) {
        int playerPower =
                CoreApi.getInstance().getPowerOfRank(
                        CoreApi.getInstance().getHighestRankIdOfPlayer(
                                CoreApi.getInstance().getPlayerId(p.getUniqueId().toString())));

        String correctPlayerPower = String.format("%07d", (1000000 - playerPower));

        String playerAbbreviation = p.getName().substring(0, 2);

        String correctPlayerLevel = String.format("%03d",
                CoreApi.getInstance().getSkyblockLevelFromPlayerId(
                        CoreApi.getInstance().getPlayerId(p.getUniqueId().toString())));

        return correctPlayerPower + correctPlayerLevel + playerAbbreviation;
    }

}
