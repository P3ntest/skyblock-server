package de.razey.mc.skyblock.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SkyblockEntityDamageEvent implements Listener {

    @EventHandler
    public void onPlayerDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getDamage() >= player.getHealth()) {
                e.setCancelled(true);
                player.setHealth(1);
            }
        }
    }
}
