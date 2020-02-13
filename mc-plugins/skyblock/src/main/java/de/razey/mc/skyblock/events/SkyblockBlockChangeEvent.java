package de.razey.mc.skyblock.events;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.logic.ActionChecker;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class SkyblockBlockChangeEvent implements Listener {



    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(!ActionChecker.mayPerform(e.getPlayer(), e.getBlock().getLocation()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(!ActionChecker.mayPerform(e.getPlayer(), e.getBlock().getLocation()));
    }

}
