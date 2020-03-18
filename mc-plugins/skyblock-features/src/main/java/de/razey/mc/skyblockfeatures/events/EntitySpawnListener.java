package de.razey.mc.skyblockfeatures.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (e.getEntityType() == EntityType.PHANTOM)
            e.setCancelled(true);
        if (e.getEntityType() == EntityType.WANDERING_TRADER)
            e.setCancelled(true);
        if (e.getEntityType() == EntityType.TRADER_LLAMA)
            e.setCancelled(true);
    }

}
