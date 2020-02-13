package de.razey.mc.elevators.events;

import de.razey.mc.elevators.Utils.ItemBuilder;
import de.razey.mc.elevators.Utils.data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(true) {//Die NBT abfrage hinzufügen
            e.setCancelled(true);
            p.getInventory().addItem(new ItemBuilder(Material.DAYLIGHT_DETECTOR).setDisplayName(data.elevatorName).build());
        }
    }
}
