package de.razey.mc.skyblock.commands;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyblockIslandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (IslandCreator.getIslandPosition(player.getUniqueId().toString()) == -1) {
            IslandCreator.createIslandForPlayer(player);
            return true;
        }

        if (args.length == 0) {
            player.teleport(IslandCreator.getIslandSpawn(IslandCreator.getIslandPosition(player.getUniqueId().toString())));
            return true;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("delete")) {
                if (args.length == 1) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.delete.confirm", "skyblock");
                    return true;
                }
                if (args[1].equalsIgnoreCase(   "confirm")) {
                    IslandCreator.eraseIsland(player);
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.delete.done", "skyblock");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("sethome")) {
                if (!player.getWorld().equals(Bukkit.getWorld("islands"))) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.sethome.wrong-world", "skyblock");
                    return true;
                }
                if (!IslandCreator.isOnOwnIsland(player)) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.sethome.wrong-island", "skyblock");
                    return true;
                }

                IslandCreator.setIslandSpawn(IslandCreator.getIslandOfLocation(player.getLocation()), player.getLocation());
                CoreApi.getInstance().displayMessage(player, "skyblock.island.sethome.done", "skyblock");
                return true;
            }

            if (args[0].equalsIgnoreCase("add")) {
                if(args.length == 1) {
                   CoreApi.getInstance().displayMessage(player, "skyblock.island.no-player", "skyblock");
                   return true;
                }
                int playerToAdd = CoreApi.getInstance().getPlayerIdFromName(args[1]);
                if(playerToAdd == -1) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.wrong-id", "skyblock");
                    return true;
                }
                else {
                    IslandCreator.setPlayerRank(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()), playerToAdd, "add");
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.add-" + args[1], "skyblock"); //message name
                    if (Bukkit.getPlayer(CoreApi.getInstance().getUuidOfPlayerId(playerToAdd)) {
                        CoreApi.getInstance().displayMessage(CoreApi.getInstance().getPlayerNameFromId(), "skyblock.island.", "skyblock");
                    }
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("promote")) {
                if(args.length == 1) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.no-player", "skyblock");
                    return true;
                }
                int playerToAdd = 0;//CoreApi.getInstance().getPlayerId();
                if(playerToAdd == -1) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.wrong-id", "skyblock");
                    return true;
                }
                else {
                    switch (IslandCreator.getPlayerRank(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()), playerToAdd)){
                        case "add":
                            IslandCreator.setPlayerRank(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()), playerToAdd, "trust");
                            break;
                        case "trust":
                            IslandCreator.setPlayerRank(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()), playerToAdd, "mod");
                            break;
                    }
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("demote")) {
                if(args.length == 1) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.no-player", "skyblock");
                    return true;
                }
                int playerToAdd = 0;//CoreApi.getInstance().getPlayerId();
                if(playerToAdd == -1) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.wrong-id", "skyblock");
                    return true;
                }
                else {
                    switch (IslandCreator.getPlayerRank(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()), playerToAdd)){
                        case "add":
                            IslandCreator.setPlayerRank(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()), playerToAdd, "");
                            break;
                        case "trust":
                            IslandCreator.setPlayerRank(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()), playerToAdd, "add");
                            break;
                        case "mod":
                            IslandCreator.setPlayerRank(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()), playerToAdd, "trust");
                            break;
                    }
                    return true;
                }
            }

            return true;
        }

        sender.sendMessage("Befehl nicht gefunden.");
        return false;
    }
}
