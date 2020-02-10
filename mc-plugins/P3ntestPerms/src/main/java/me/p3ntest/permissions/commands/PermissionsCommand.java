package me.p3ntest.permissions.commands;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("pperm.command")) {
            CoreApi.getInstance().displayMessage((Player) sender, "nopermission", null);
            return true;
        }
        return false;
    }
}
