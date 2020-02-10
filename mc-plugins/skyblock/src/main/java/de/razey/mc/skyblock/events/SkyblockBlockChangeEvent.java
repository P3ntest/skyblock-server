package de.razey.mc.skyblock.events;

import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class SkyblockBlockChangeEvent implements Listener {

    private boolean mayPerform(Player player, Location targetBlock) {
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

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(!mayPerform(e.getPlayer(), e.getBlock().getLocation()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(!mayPerform(e.getPlayer(), e.getBlock().getLocation()));
    }

}
