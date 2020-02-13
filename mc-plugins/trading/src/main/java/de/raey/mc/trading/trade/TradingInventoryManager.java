package de.raey.mc.trading.trade;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class TradingInventoryManager {

    public static List<Trade> trades = new ArrayList<>();

    public static HashMap<Player, Player> tradeRequests;

    public static Trade getTradePlayerIsIn(Player p) {
        for (Trade trade : trades) {
            if (trade.isPlayerInTrade(p)) {
                return trade;
            }
        }
        return null;
    }

    public static void playerCloseInventory(Player p) {
        Trade trade = getTradePlayerIsIn(p);
        if (trade == null) {
            return;
        }
        trade.tradeForceEnd();
    }
}