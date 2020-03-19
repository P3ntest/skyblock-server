package de.razey.mc.skyblockquests.events;

import de.razey.mc.skyblockquests.npc.NPC;
import de.razey.mc.skyblockquests.npc.VanillaEntityNPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventsListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        for (NPC npc : VanillaEntityNPC.all) {
            if (npc instanceof VanillaEntityNPC) {
                if (event.getEntity().getEntityId() == ((VanillaEntityNPC) npc).getSpawned().getEntityId()) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
