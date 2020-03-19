package de.razey.mc.skyblock.events;

import de.razey.mc.skyblock.logic.ActionChecker;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SkyblockPlayerInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        switch (event.getRightClicked().getType()) {
            case SHEEP:
            case COW:
            case ITEM_FRAME:
                event.setCancelled(!ActionChecker.mayPerform(event.getPlayer(), event.getRightClicked().getLocation()));
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType().equals(Material.valueOf("FARMLAND")))
            event.setCancelled(true);

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (event.getClickedBlock().getType()) {
                case CHEST:
                    event.setCancelled(!ActionChecker.mayOpenChest(event.getPlayer(), event.getClickedBlock().getLocation()));
                    break;
                case ITEM_FRAME:
                case LEVER:
                case STONE_BUTTON:
                case FURNACE:
                case COMPOSTER:
                case TRAPPED_CHEST:
                case FLOWER_POT:
                default:
                    event.setCancelled(!ActionChecker.mayPerform(event.getPlayer(), event.getClickedBlock().getLocation()));
                    break;
            }
        }
    }
}
