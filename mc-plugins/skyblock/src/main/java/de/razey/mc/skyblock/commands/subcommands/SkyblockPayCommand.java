package de.razey.mc.skyblock.commands.subcommands;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyblockPayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 2) {
            CoreApi.getInstance().displayMessage((Player) sender, "skyblock.balance.pay.few-args", "skyblock");
        }

        return false;
    }
}
