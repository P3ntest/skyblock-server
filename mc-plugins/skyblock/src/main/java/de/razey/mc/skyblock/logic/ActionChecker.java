package de.razey.mc.skyblock.logic;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.hamcrest.core.Is;

public abstract class ActionChecker {

    public static boolean mayOpenChest(Player player, Location loc) {
        if (player.getWorld() != Bukkit.getWorld("islands")) {
            return false;
        }
        Block sign = null;
        if (loc.getBlock().getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) {
            sign = loc.getBlock().getRelative(BlockFace.NORTH);
        }
        if (loc.getBlock().getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN) {
            sign = loc.getBlock().getRelative(BlockFace.SOUTH);
        }
        if (loc.getBlock().getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN) {
            sign = loc.getBlock().getRelative(BlockFace.WEST);
        }
        if (loc.getBlock().getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN) {
            sign = loc.getBlock().getRelative(BlockFace.EAST);
        }

        if (sign != null) {
            Sign aSign = (Sign) sign;

            int island = IslandCreator.getIslandOfLocation(loc);
            if (island == -1)
                return false;

            int owner = IslandCreator.getIslandOwner(island);

            if (owner == -1)
                return false;

            int playerId = CoreApi.getInstance().getPlayerIdFromUuid(player.getUniqueId().toString());

            if (playerId == owner)
                return true;

            if (aSign.getLine(0).equalsIgnoreCase("[island]")
                    || aSign.getLine(0).equalsIgnoreCase("[is]")) {
                if (IslandCreator.getPlayerRank(owner, playerId) == "trust"
                        || IslandCreator.getPlayerRank(owner, playerId) == "mod") {
                    return true;
                } else {
                    return false;
                }
            }

            if (aSign.getLine(0).equalsIgnoreCase("[player]")
                    || aSign.getLine(0).equalsIgnoreCase("[players]")) {
                if (signContainsName(player.getName().toLowerCase(), aSign))
                    return true;
                return false;
            }

        }
        return mayPerform(player, loc);
    }

    private static boolean signContainsName(String name, Sign sign) {
        if (sign.getLine(1).equalsIgnoreCase(name))
            return true;
        if (sign.getLine(2).equalsIgnoreCase(name))
            return true;
        if (sign.getLine(3).equalsIgnoreCase(name))
            return true;

        return false;
    }

    public static boolean mayPerform(Player player, Location targetBlock) {
        if (player.getWorld() != Bukkit.getWorld("islands")) {
            return false;
        }

        if (IslandCreator.getIslandOfLocation(targetBlock) == -1) {
            return false;
        }

        if (IslandCreator.mayBuildOnIsland(player, IslandCreator.getIslandOfLocation(targetBlock))) {
            return true;
        }

        return false;
    }

}