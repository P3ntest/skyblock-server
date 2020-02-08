package de.razey.mc.skyblock.commands;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hamcrest.core.Is;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkyblockIslandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        try {
            PreparedStatement doesUserHaveIslandStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                    "SELECT position FROM islands WHERE owner=?"
            );
            doesUserHaveIslandStatement.setInt(1, CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()));
            ResultSet doesUserHaveIslandResult = doesUserHaveIslandStatement.executeQuery();

            if (!doesUserHaveIslandResult.next()) {
                IslandCreator.createIslandForPlayer(player);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (args.length == 0) {
            try {
                PreparedStatement getIslandSpawnStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                        "SELECT `home_x`, `home_y`, `home_z` FROM islands WHERE owner=?"
                );
                getIslandSpawnStatement.setInt(1, CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()));
                ResultSet getIslandSpawnResult = getIslandSpawnStatement.executeQuery();
                getIslandSpawnResult.next();
                player.teleport(new Location(
                        Bukkit.getWorld("islands"),
                        getIslandSpawnResult.getFloat(1),
                        getIslandSpawnResult.getFloat(2),
                        getIslandSpawnResult.getFloat(3)));
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 1) {
                    sender.sendMessage("Sicher? (/is remove confirm)");
                    return true;
                }
                if (args[1].equalsIgnoreCase("confirm")) {
                    IslandCreator.eraseIsland(player);
                    sender.sendMessage("Insel gelöscht");
                    return true;
                }
            }

            return true;
        }

        sender.sendMessage("Befehl nicht gefunden.");
        return false;
    }
}
