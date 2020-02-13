package de.razey.mc.trading.commands;

import de.razey.mc.trading.trade.Trade;
import de.razey.mc.trading.trade.TradingInventoryManager;
import de.razey.mc.core.api.CoreApi;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (args.length == 0) {
            if (TradingInventoryManager.tradeRequests.containsKey(player)) {
                CoreApi.getInstance().displayMessage(player, "trading.self.info-trade", "trading", TradingInventoryManager.tradeRequests.get(player).getName());
                return true;
            } else {
                CoreApi.getInstance().displayMessage(player, "trading.no-player", "trading");
                return true;
            }
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("cancel")) {
                if (TradingInventoryManager.tradeRequests.containsKey(player)) {
                    CoreApi.getInstance().displayMessage(player, "trading.trade-cancel.done", "trading");
                    TradingInventoryManager.tradeRequests.remove(player);
                    return true;
                } else {
                    CoreApi.getInstance().displayMessage(player, "trading.no-trade-to-cancel", "trading");
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("accept")) {
                List<Player> allTradeRequestsFromPlayer = new ArrayList<>();
                for (Map.Entry<Player, Player> set : TradingInventoryManager.tradeRequests.entrySet()) {
                    if (set.getValue() == player) {
                        allTradeRequestsFromPlayer.add(set.getKey());
                    }
                }
                if (allTradeRequestsFromPlayer.size() == 0) {
                    CoreApi.getInstance().displayMessage(player, "trading.no-trade-to-accept", "trading");
                    return true;
                }
                if (allTradeRequestsFromPlayer.size() == 1) {
                    TradingInventoryManager.trades.add(new Trade(player, allTradeRequestsFromPlayer.get(0)));
                    TradingInventoryManager.tradeRequests.remove(allTradeRequestsFromPlayer.get(0));
                    return true;
                }

                CoreApi.getInstance().displayMessage(player, "trading.accept.multiple", "trading");
                for (Player p : allTradeRequestsFromPlayer) {
                    CoreApi.getInstance().displayMessage(player, "trading.accept.multiple.item", "trading", p.getName());
                }
                return true;
            }

            if (TradingInventoryManager.tradeRequests.containsKey(player)) {
                CoreApi.getInstance().displayMessage(player, "trading.request.already-request", "trading");
                return true;
            }

            Player toRequest = Bukkit.getPlayer(args[0]);

            if (toRequest == null) {
                CoreApi.getInstance().displayMessage(player, "trading.request.player-not-found", "trading");
                return true;
            }

            TradingInventoryManager.tradeRequests.put(player, toRequest);

            CoreApi.getInstance().displayMessage(toRequest, "trading.request.self", "trading", player.getName());
            CoreApi.getInstance().displayMessage(player, "trading.request.other", "trading", toRequest.getName());
        }

        if (args[0].equalsIgnoreCase("accept")) {
            Player keyToRemove = null;
            for (Map.Entry<Player, Player> set : TradingInventoryManager.tradeRequests.entrySet()) {
                if (set.getValue() == player) {
                    if (set.getKey().getName().equalsIgnoreCase(args[1])) {
                        TradingInventoryManager.trades.add(new Trade(set.getKey(), set.getValue()));
                        keyToRemove = set.getKey();
                    }
                }
            }
            if (keyToRemove != null) {
                TradingInventoryManager.tradeRequests.remove(keyToRemove);
                return true;
            }
            CoreApi.getInstance().displayMessage(player, "trading.accept.no-request-from-player", "trading", args[1]);
            return true;
        }

        CoreApi.getInstance().displayMessage(player, "trading.invalid-syntax", "trading");

        return false;
    }
}
