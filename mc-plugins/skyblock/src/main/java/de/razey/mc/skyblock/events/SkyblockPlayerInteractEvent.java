package de.razey.mc.skyblock.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SkyblockPlayerInteractEvent implements Listener {
    @EventHandler
    public void noUproot(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType().equals(Material.valueOf("SOIL")))
            event.setCancelled(true);
    }
}
