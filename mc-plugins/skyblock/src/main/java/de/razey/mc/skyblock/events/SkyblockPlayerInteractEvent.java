package de.razey.mc.skyblock.events;

import de.razey.mc.skyblock.logic.ActionChecker;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType().equals(Material.valueOf("SOIL")))
            event.setCancelled(true);

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (event.getClickedBlock().getType()) {
                case ITEM_FRAME:
                case LEVER:
                case WOOD_BUTTON:
                case STONE_BUTTON:
                case WOODEN_DOOR:
                case WOOD_DOOR:
                case TRAP_DOOR:
                case TRAPPED_CHEST:
                case CHEST:
                    event.setCancelled(!ActionChecker.mayPerform(event.getPlayer(), event.getClickedBlock().getLocation()));
                    break;
            }
        }
    }
}
