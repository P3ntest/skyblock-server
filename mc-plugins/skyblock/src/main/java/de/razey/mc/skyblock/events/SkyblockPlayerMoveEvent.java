package de.razey.mc.skyblock.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SkyblockPlayerMoveEvent implements Listener {



    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getTo().getY() < 0) {
            Location location = new Location(e.getTo().getWorld(), e.getTo().getX(), 256, e.getTo().getY());
            e.getPlayer().teleport(location);
        }
    }
}
