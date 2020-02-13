package de.raey.mc.trading.commands;

import de.raey.mc.trading.trade.TradingInventoryManager;
import de.razey.mc.core.api.CoreApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        return false;
    }
}
