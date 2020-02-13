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
        String playerName = CoreApi.getInstance().getPlayerNameFromId(playerId);
        String playerToAddName = CoreApi.getInstance().getCorrectPlayerName(args[1]);
        String playerToAddUuid = CoreApi.getInstance().getUuidFromPlayerId(playerToAddId);
        Player playerToAdd = Bukkit.getPlayer(UUID.fromString(playerToAddUuid));

        switch (args[0]){
            case "add":
                IslandCreator.setPlayerRank(playerId, playerToAddId, "add");
                CoreApi.getInstance().displayMessage(player, "skyblock.island.add.other.done", "skyblock", playerToAddName);
                if (playerToAdd != null)
                    CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.add.self.done", "skyblock", playerName);
                return true;
            case "promote":
                switch (IslandCreator.getPlayerRank(playerId, playerToAddId)){
                    case "":
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.not-added", "skyblock", playerToAddName);
                        break;
                    case "add":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "trust");
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.trust.done", "skyblock", playerToAddName);
                        if (playerToAdd != null)
                            CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.promote.self.trust.done", "skyblock", playerName);
                        break;
                    case "trust":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "mod");
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.mod.done", "skyblock", playerToAddName);
                        if (playerToAdd != null)
                            CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.promote.self.mod.done", "skyblock", playerName);
                        break;
                    case "mod":
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.max", "skyblock", playerToAddName);
                        break;
                }
                return true;
            case "demote":
                switch (IslandCreator.getPlayerRank(playerId, playerToAddId)){
                    case "add":
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.min", "skyblock");
                        break;
                    case "trust":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "add");
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.add.done", "skyblock", playerToAddName);
                        if (playerToAdd != null)
                            CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.demote.self.add.done", "skyblock", playerToAddName);
                        break;
                    case "mod":
                        IslandCreator.setPlayerRank(playerId, playerToAddId, "trust");
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.trust.done", "skyblock", playerToAddName);
                        if (playerToAdd != null)
                            CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.demote.self.trust.done", "skyblock", playerToAddName);
                        break;
                    case "":
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.no-rank", "skyblock", playerToAddName);
                }
                return true;
            case "remove":
                IslandCreator.setPlayerRank(playerId, playerToAddId, "");
                CoreApi.getInstance().displayMessage(player, "skyblock.island.remove.other.done", "skyblock", playerToAddName);
                if (playerToAdd != null)
                    CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.remove.self.done", "skyblock", playerName);
                break;
        }
        return true;
    }
}
