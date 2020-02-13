package de.razey.mc.trading.events;

import de.razey.mc.trading.trade.TradingInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerInventoryEvents implements Listener {

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        TradingInventoryManager.playerCloseInventory((Player) e.getPlayer());
    }

}
