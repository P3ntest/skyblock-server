package de.razey.mc.skyblock.commands.subcommands;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        Location playerLocation = player.getLocation();
        int island = IslandCreator.getIslandOfLocation(playerLocation);
        int ownerId = IslandCreator.getIslandOwner(island);
        String ownerName = CoreApi.getInstance().getPlayerNameFromId(ownerId);
        String playerToAddName = CoreApi.getInstance().getCorrectPlayerName(args[1]);
        String playerToAddUuid = CoreApi.getInstance().getUuidFromPlayerId(playerToAddId);
        Player playerToAdd = Bukkit.getPlayer(UUID.fromString(playerToAddUuid));

        switch (args[0]){
            case "add":
                IslandCreator.setPlayerRank(ownerId, playerToAddId, "add");
                CoreApi.getInstance().displayMessage(player, "skyblock.island.add.other.done", "skyblock", playerToAddName);
                if (playerToAdd != null) {
                    if (playerId == ownerId) {
                        CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.add.self.done.by-owner", "skyblock", ownerName);
                    } else
                        CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.add.self.done", "skyblock", playerName, ownerName);
                }
                return true;
            case "promote":
                switch (IslandCreator.getPlayerRank(ownerId, playerToAddId)){
                    case "":
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.not-added", "skyblock", playerToAddName);
                        break;
                    case "add":
                        IslandCreator.setPlayerRank(ownerId, playerToAddId, "trust");
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.trust.done", "skyblock", playerToAddName);
                        if (playerToAdd != null) {
                            if (playerId == ownerId) {
                                CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.self.trust.done.by-owner", "skyblock", ownerName);
                            } else
                                CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.promote.self.trust.done", "skyblock", playerName, ownerName);
                        }
                        break;
                    case "trust":
                        IslandCreator.setPlayerRank(ownerId, playerToAddId, "mod");
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.mod.done", "skyblock", playerToAddName);
                        if (playerToAdd != null)
                            if (playerId == ownerId) {
                                CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.promote.self.mod.done.by-owner", "skyblock", ownerName);
                            } else
                                CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.promote.self.mod.done", "skyblock", playerName, ownerName);
                        break;
                    case "mod":
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.max", "skyblock", playerToAddName);
                        break;
                }
                return true;
            case "demote":
                switch (IslandCreator.getPlayerRank(ownerId, playerToAddId)){
                    case "add":
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.min", "skyblock");
                        break;
                    case "trust":
                        IslandCreator.setPlayerRank(ownerId, playerToAddId, "add");
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.add.done", "skyblock", playerToAddName);
                        if (playerToAdd != null)
                            if (playerId == ownerId) {
                                CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.demote.self.add.done.by-owner", "skyblock", ownerName);
                            } else
                                CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.demote.self.add.done", "skyblock", playerName, ownerName);
                        break;
                    case "mod":
                        IslandCreator.setPlayerRank(ownerId, playerToAddId, "trust");
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.trust.done", "skyblock", playerToAddName);
                        if (playerToAdd != null)
                            if (playerId == ownerId) {
                                CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.demote.self.trust.done.by-owner", "skyblock", ownerName);
                            } else
                                CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.promote.self.trust.done", "skyblock", playerName, ownerName);
                        break;
                    case "":
                        CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.no-rank", "skyblock", playerToAddName);
                }
                return true;
            case "remove":
                IslandCreator.setPlayerRank(ownerId, playerToAddId, "");
                CoreApi.getInstance().displayMessage(player, "skyblock.island.remove.other.done", "skyblock", playerToAddName);
                if (playerToAdd != null)
                    if (playerId == ownerId) {
                        CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.remove.self.done.by-owner", "skyblock", ownerName);
                    } else
                        CoreApi.getInstance().displayMessage(playerToAdd, "skyblock.island.remove.self.done", "skyblock", playerName, ownerName);
                break;
        }
        return true;
    }
}