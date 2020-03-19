package de.razey.mc.skyblock.commands;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.Bukkit;
import org.bukkit.command.BufferedCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyblockPayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length < 2) {
            CoreApi.getInstance().displayMessage((Player) sender, "skyblock.balance.pay.few-args", "skyblock");
            return true;
        }

        Player toPayTo = Bukkit.getPlayer(args[0]);

        if (toPayTo == null) {
            CoreApi.getInstance().displayMessage((Player) sender, "skyblock.balance.pay.player-not-online", "skyblock");
            return true;
        }

        float toPay = -1;
        String mArg = args[1].replaceAll(",", ".");
        try {
            toPay = Float.parseFloat(mArg);
        } catch (Exception e) {

        }

        if (toPay <= 0) {
            CoreApi.getInstance().displayMessage((Player) sender,
                    "skyblock.balance.pay.invalid-amount", "skyblock");
            return true;
        }

        int senderMoney = (int) CoreApi.getInstance().getSkyblockBalanceFromPlayerId(
                CoreApi.getInstance().getPlayerIdFromPlayer(player));

        if (senderMoney < toPay) {
            CoreApi.getInstance().displayMessage((Player) sender,
                    "skyblock.balance.pay.invalid-balance", "skyblock");
        }

        CoreApi.getInstance().modifySkyblockBalanceFromPlayerId(
                CoreApi.getInstance().getPlayerIdFromPlayer(player), -toPay);
        CoreApi.getInstance().modifySkyblockBalanceFromPlayerId(
                CoreApi.getInstance().getPlayerIdFromPlayer(toPayTo), toPay);

        CoreApi.getInstance().displayMessage(player,
                "skyblock.balance.pay.send", "skyblock", toPayTo.getName(), toPay + "");
        CoreApi.getInstance().displayMessage(toPayTo,
                "skyblock.balance.pay.recieve", "skyblock", player.getName(), toPay + "");


        return false;
    }
}
