package de.razey.mc.elevators.events;

import de.razey.mc.elevators.Utils.data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(e.getBlock().getBlockData().getMaterial() == Material.DAYLIGHT_DETECTOR && e.getItemInHand().getItemMeta().getDisplayName() == data.elevatorName);
    }
}
