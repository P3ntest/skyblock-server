package de.razey.mc.trading.events;

import de.razey.mc.trading.trade.Trade;
import de.razey.mc.trading.trade.TradingInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInventoryEvents implements Listener {

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        TradingInventoryManager.playerCloseInventory((Player) e.getPlayer());
    }

    @EventHandler
    public void onPlayerInventoryInteract(InventoryMoveItemEvent e) {
        //TODO Alle anderen Inventory Events abfangen und mit einer RestApi handeln
    }

    @EventHandler
    public void  onPlayerInventoryClick(InventoryClickEvent e){
        if(Trade.isInvTradeInv(e.getInventory())){
            Player p =(Player) e.getWhoClicked();
            if(TradingInventoryManager.trades.contains(p)){
                if(Trade.notAllowedToClick(e.getSlot())){
                    if(e.getSlot() <= 4 ) {
                        Trade.getTradeFromPlayer(p).accept(p);
                        e.setCancelled(true);
                    }
                     else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

}
