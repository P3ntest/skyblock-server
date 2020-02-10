package de.razey.mc.skyblock.commands;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyblockBalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 0) {
            if (!(sender instanceof Player))
                return true;

            Player player = (Player) sender;

            CoreApi.getInstance().displayMessage(player, "skyblock.balance.display", null,
                    "" + CoreApi.getInstance().getSkyblockBalanceFromPlayerId(CoreApi.getInstance().getPlayerId(player.getUniqueId().toString())));
        }

        return false;
    }
}
