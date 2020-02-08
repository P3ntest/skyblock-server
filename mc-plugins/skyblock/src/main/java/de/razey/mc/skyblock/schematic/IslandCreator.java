package de.razey.mc.skyblock.schematic;

import com.boydti.fawe.util.EditSessionBuilder;
import com.google.gson.internal.$Gson$Preconditions;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.ClipboardFormats;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class IslandCreator {

    public static final int islandPadding = 2000;

    private static int getFirstValidPosition() {
        try {
            PreparedStatement getAllPositionsStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                    "SELECT position FROM islands ORDER BY position"
            );
            ResultSet getAllPositionsResult = getAllPositionsStatement.executeQuery();

            int at = 0;

            while (getAllPositionsResult.next() && getAllPositionsResult.getInt(1) == at) {
                at++;
            }
            return at;
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 99;
    }

    private static void spawnIsland(Location location) {
        File file = new File(Main._instance.getDataFolder() + "/island.schematic");

        Vector position = new Vector(location.getBlockX(),location.getBlockY(), location.getBlockZ());

        World world = new BukkitWorld(Bukkit.getWorld("islands"));

        try {
            EditSession editSession = Objects.requireNonNull(ClipboardFormats.findByFile(file)).load(file).paste(world, position, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getIslandPosition(String uuid) throws SQLException {
        PreparedStatement getIslandPositionStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                "SELECT position FROM islands WHERE owner=?"
        );
        getIslandPositionStatement.setInt(1, CoreApi.getInstance().getPlayerId(uuid));
        ResultSet getIslandPositionResult = getIslandPositionStatement.executeQuery();
        getIslandPositionResult.next();
        return getIslandPositionResult.getInt(1);
    }

    private static Location centerLocation(Location location) {
        location.setX(location.getX() + 0.5);
        location.setY(location.getY() + 0.5);
        location.setZ(location.getZ() + 0.5);

        return location;
    }

    public static void removeIslandFromDataBase(int position) throws SQLException {
        PreparedStatement enterIslandToDatabaseStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                "DELETE FROM islands WHERE position=?"
        );

        enterIslandToDatabaseStatement.setInt(1, position);
        enterIslandToDatabaseStatement.executeUpdate();
    }

    public static void eraseIsland(Player p) {
        int position = -1;
        try {
            position = getIslandPosition(p.getUniqueId().toString());
        }   catch (SQLException e) {
            e.printStackTrace();
        }

        World world = new BukkitWorld(Bukkit.getWorld("islands"));
        EditSession editSession = new EditSessionBuilder(world).fastmode(true).build();

        Vector point1 = new Vector((position * islandPadding) - 251, 0, -251);
        Vector point2 = new Vector((position * islandPadding) + 251, 255, 251);

        Region region = new CuboidRegion(world, point1, point2);

        editSession.setBlocks(region, new BaseBlock(0));

        editSession.flushQueue();

        try {
            removeIslandFromDataBase(position);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void enterIslandToDatabase(Player player, Location location, int position) throws SQLException {
        PreparedStatement enterIslandToDatabaseStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                "INSERT INTO `islands`(`owner`, `position`, `home_x`, `home_y`, `home_z`) " +
                        "VALUES (?, ?, ?, ?, ?)"
        );

        Location spawn = centerLocation(location);

        enterIslandToDatabaseStatement.setInt(1, CoreApi.getInstance().getPlayerId(player.getUniqueId().toString()));
        enterIslandToDatabaseStatement.setInt(2, position);
        enterIslandToDatabaseStatement.setFloat(3, (float) spawn.getX());
        enterIslandToDatabaseStatement.setFloat(4, (float) spawn.getY());
        enterIslandToDatabaseStatement.setFloat(5, (float) spawn.getZ());

        enterIslandToDatabaseStatement.executeUpdate();
    }

    public static void createIslandForPlayer(Player player) {
        int position = getFirstValidPosition();
        Location location = new Location(Bukkit.getWorld("islands"), position * islandPadding, 100, 0);
        try {
            enterIslandToDatabase(player, location, position);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            spawnIsland(location);
            player.teleport(location);
        }
    }

}
