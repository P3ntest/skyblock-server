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

        int playerId = CoreApi.getInstance().getPlayerIdFromName(args[1]);

        if (playerId == -1) {
            CoreApi.getInstance().displayMessage(player, "skyblock.island.player-not-found", "skyblock");
            return true;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if(args.length == 1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.island.no-player", "skyblock");
                return true;
            }

            int playerid = CoreApi.getInstance().getPlayerId(player.getUniqueId().toString());
            String name = CoreApi.getInstance().getCorrectPlayerName(args[1]);
            int playerToAdd = CoreApi.getInstance().getPlayerIdFromName(args[1]);
            Player playerToAddUuid = Bukkit.getPlayer(UUID.fromString(CoreApi.getInstance().getUuidOfPlayerId(playerToAdd)));

            if(playerToAdd == -1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.island.wrong-id", "skyblock");
                return true;
            }
            else {
                IslandCreator.setPlayerRank(playerid, playerToAdd, "add");
                CoreApi.getInstance().displayMessage(player, "skyblock.island.add.other.done", "skyblock", name);
                if (Bukkit.getPlayer(CoreApi.getInstance().getUuidOfPlayerId(playerToAdd)) != null) {
                    CoreApi.getInstance().displayMessage(Bukkit.getPlayer(CoreApi.getInstance().getUuidOfPlayerId(playerToAdd)), "skyblock.island.add.self.done", "skyblock");
                }
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("promote")) {
            if(args.length == 1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.island.no-player", "skyblock");
                return true;
            }

            int playerid = CoreApi.getInstance().getPlayerId(player.getUniqueId().toString());
            String name = CoreApi.getInstance().getCorrectPlayerName(args[1]);
            int playerToAdd = CoreApi.getInstance().getPlayerIdFromName(args[1]);
            Player playerToAddUuid = Bukkit.getPlayer(UUID.fromString(CoreApi.getInstance().getUuidOfPlayerId(playerToAdd)));

            if(playerToAdd == -1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.island.wrong-id", "skyblock");
                return true;
            }
            else {
                switch (IslandCreator.getPlayerRank(playerid, playerToAdd)){
                    case "add":
                        IslandCreator.setPlayerRank(playerid, playerToAdd, "trust");
                        break;
                    case "trust":
                        IslandCreator.setPlayerRank(playerid, playerToAdd, "mod");
                        break;
                }
                CoreApi.getInstance().displayMessage(player, "skyblock.island.promote.other.done", "skyblock", name);
                if (playerToAddUuid != null) {
                    CoreApi.getInstance().displayMessage(playerToAddUuid, "skyblock.island.promote.self.done", "skyblock", name, IslandCreator.getPlayerRank(playerid, playerToAdd));
                }
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("demote")) {
            if(args.length == 1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.island.no-player", "skyblock");
                return true;
            }

            int playerid = CoreApi.getInstance().getPlayerId(player.getUniqueId().toString());
            String name = CoreApi.getInstance().getCorrectPlayerName(args[1]);
            int playerToAdd = CoreApi.getInstance().getPlayerIdFromName(args[1]);
            Player playerToAddUuid = Bukkit.getPlayer(UUID.fromString(CoreApi.getInstance().getUuidOfPlayerId(playerToAdd)));

            if(playerToAdd == -1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.island.wrong-id", "skyblock");
                return true;
            }
            else {
                switch (IslandCreator.getPlayerRank(playerid, playerToAdd)){
                    case "add":
                        IslandCreator.setPlayerRank(playerid, playerToAdd, "");
                        break;
                    case "trust":
                        IslandCreator.setPlayerRank(playerid, playerToAdd, "add");
                        break;
                    case "mod":
                        IslandCreator.setPlayerRank(playerid, playerToAdd, "trust");
                        break;
                }
                CoreApi.getInstance().displayMessage(player, "skyblock.island.demote.other.done", "skyblock", name);
                if (playerToAddUuid != null) {
                    CoreApi.getInstance().displayMessage(playerToAddUuid, "skyblock.island.demote.self.done", "skyblock", name, IslandCreator.getPlayerRank(playerid, playerToAdd));
                }
                return true;
            }
        }

        return true;
    }

}
