package de.razey.mc.skyblock.events;

import de.razey.mc.skyblock.logic.ActionChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class SkyblockBucketEvent implements Listener {

    @EventHandler
    public void onBucket(PlayerBucketFillEvent e) {
        e.setCancelled(!ActionChecker.mayPerform(e.getPlayer(), e.getBlockClicked().getLocation()));
    }

    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent e) {
        e.setCancelled(!ActionChecker.mayPerform(e.getPlayer(), e.getBlockClicked().getLocation()));
    }

}
