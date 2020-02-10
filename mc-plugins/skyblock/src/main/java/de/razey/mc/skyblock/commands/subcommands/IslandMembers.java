package de.razey.mc.skyblock.commands.subcommands;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class IslandMembers {

    public static boolean memberCommand (Player player, String[] args) {

        if(args.length == 1) {
            CoreApi.getInstance().displayMessage(player, "skyblock.island.no-player", "skyblock");
            return true;
        }

        int playerToAddId = CoreApi.getInstance().getPlayerIdFromName(args[1]);

        if (playerToAddId == -1) {
            CoreApi.getInstance().displayMessage(player, "skyblock.island.player-not-found", "skyblock");
            return true;
        }

        int playerId = CoreApi.getInstance().getPlayerId(player.getUniqueId().toString());
        String playerToAddName = CoreApi.getInstance().getCorrectPlayerName(args[1]);
        String playerToAddUuid = CoreApi.getInstance().getUuidFromPlayerId(playerToAddId);
        Player playerToAdd = Bukkit.getPlayer(UUID.fromString(playerToAddUuid));

        switch (args[0]){
            case "add":
                IslandCreator.setPlayerRank(playerId, playerToAddId, "add");
                CoreApi.getInstance().displayMessage(player, "skyblock.island.add.other.done", "skyblock", playerToAddName);
                if (playerToAdd != null) {
                    CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.add.self.done", "skyblock");
                }
                return true;
            case "promote":
                switch (IslandCreator.getPlayerRank(playerId, playerToAddId)){
                    case "add":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "trust");
                        break;
                    case "trust":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "mod");
                        break;
                }
                CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.done", "skyblock", playerToAddName);
                if (playerToAdd != null) {
                    CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.promote.self.done", "skyblock", playerToAddName, IslandCreator.getPlayerRank(playerId, playerToAddId));
                }
                return true;
            case "demote":
                switch (IslandCreator.getPlayerRank(playerId, playerToAddId)){
                    case "add":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "");
                        break;
                    case "trust":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "add");
                        break;
                    case "mod":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "trust");
                        break;
                }
                CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.done", "skyblock", playerToAddName);
                if (playerToAdd != null) {
                    CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.demote.self.done", "skyblock", playerToAddName, IslandCreator.getPlayerRank(playerId, playerToAddId));
                }
                return true;

        }
        return true;
    }
}
