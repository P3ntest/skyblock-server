package de.razey.mc.skyblockquests.events;

import de.razey.mc.skyblockquests.npc.NPC;
import de.razey.mc.skyblockquests.npc.VanillaEntityNPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractEventListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        for (NPC npc : NPC.all) {
            if (npc instanceof VanillaEntityNPC) {
                if (event.getRightClicked().getEntityId() == ((VanillaEntityNPC) npc).getSpawned().getEntityId()) {
                    VanillaEntityNPC vNpc = (VanillaEntityNPC) npc;
                    if (vNpc.cancelClickEvent())
                        event.setCancelled(true);
                    npc.clickedOn(event.getPlayer());
                }
            }
        }
    }

}
