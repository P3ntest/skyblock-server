package de.razey.customitems.events;

import com.google.gson.internal.$Gson$Preconditions;
import de.razey.customitems.item.BottomlessWaterbucket;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.EventListenerProxy;

public class BucketEventsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!event.isCancelled()) {
            if (event.getPlayer().getInventory().getItem(event.getHand()).getItemMeta().getItemFlags().contains(ItemFlag.HIDE_PLACED_ON)) {
                if (event.getPlayer().getInventory().getItem(event.getHand()).getItemMeta().getDisplayName().equalsIgnoreCase(new BottomlessWaterbucket().getDisplayName())) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.WATER);
                }
            }
        }
    }

}
