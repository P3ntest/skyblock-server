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

        if (args.length == 0) {
            CoreApi.getInstance().displayMessage((Player) sender, "permission.nosubcommand", null);
            return true;
        }

        if (args[0].equalsIgnoreCase("user")) {
            if (args.length == 1) {
                CoreApi.getInstance().displayMessage((Player) sender, "permission.no-user", null);
                return true;
            }

            int playerId = CoreApi.getInstance().getPlayerIdFromName(args[1]);

            if (playerId == -1) {
                CoreApi.getInstance().displayMessage((Player) sender, "permission.player-not-found", null);
                return true;
            }

            if (args.length == 2) {
                CoreApi.getInstance().displayMessage((Player) sender, "permission.display-permissions-from-user", null);
                CoreApi.getInstance().getper
                return true;
            }
        }

        return false;
    }
}
