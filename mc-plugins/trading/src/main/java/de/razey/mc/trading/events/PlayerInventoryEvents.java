package de.razey.mc.trading.events;

import de.razey.mc.trading.trade.TradingInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class PlayerInventoryEvents implements Listener {

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        TradingInventoryManager.playerCloseInventory((Player) e.getPlayer());
    }

    @EventHandler
    public void onPlayerInventoryInteravt(InventoryMoveItemEvent e) {
        //TODO Alle anderen Inventory Events abfangen und mit einer RestApi handeln
    }

}
