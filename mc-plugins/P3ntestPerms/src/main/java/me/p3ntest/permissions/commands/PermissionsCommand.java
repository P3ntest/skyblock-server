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
            CoreApi.getInstance().displayMessage((Player) sender, "permission.nosubcommand", "permissions");
            return true;
        }

        if (args[0].equalsIgnoreCase("user") || args[0].equalsIgnoreCase("u")) {
            if (args.length == 1) {
                CoreApi.getInstance().displayMessage((Player) sender, "permission.no-user", "permissions");
                return true;
            }

            int playerId = CoreApi.getInstance().getPlayerIdFromName(args[1]);

            if (playerId == -1) {
                CoreApi.getInstance().displayMessage((Player) sender, "permission.player-not-found", "permissions");
                return true;
            }

            if (args.length == 2) {
                CoreApi.getInstance().displayMessage((Player) sender, "permission.display-permissions-from-user", "permissions",
                        CoreApi.getInstance().getCorrectPlayerName(args[1]));

                CoreApi.getInstance().getPlayerOnlyPermissions(playerId).forEach((permission) -> {
                    CoreApi.getInstance().displayMessage((Player) sender, "permission.display-item", null, permission);
                });

                CoreApi.getInstance().getPlayerRanks(playerId).forEach((rank) -> {
                    sender.sendMessage("");
                    CoreApi.getInstance().displayMessage((Player) sender, "permission.display-permissions-inherit-from-rank", null,
                            CoreApi.getInstance().getRankDisplayNameFromId(rank));
                    CoreApi.getInstance().getRankPermissions(rank).forEach((permission) -> {
                        CoreApi.getInstance().displayMessage((Player) sender, "permission.display-item", null, permission);
                    });
                });
                return true;
            }

            if (args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("grant") || args[2].equalsIgnoreCase("a")) {
                if (args.length == 3) {
                    CoreApi.getInstance().displayMessage((Player) sender, "permission.no-permissions-select", "permissions");
                    return true;
                }
                if (CoreApi.getInstance().playerHasPermissionSpecifically(playerId, args[3])) {
                    CoreApi.getInstance().displayMessage((Player) sender, "permission.add.player-already-has-permission", "permissions");
                    return true;
                }
                CoreApi.getInstance().addPlayerPermission(playerId, args[3].toLowerCase());
                CoreApi.getInstance().displayMessage((Player) sender, "permission.add.other.done", "permissions");
                return true;
            }

            if (args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("revoke") || args[2].equalsIgnoreCase("r")) {
                if (args.length == 3) {
                    CoreApi.getInstance().displayMessage((Player) sender, "permission.no-permissions-select", "permissions");
                    return true;
                }
                if (!CoreApi.getInstance().playerHasPermissionSpecifically(playerId, args[3])) {
                    CoreApi.getInstance().displayMessage((Player) sender, "permission.revoke.player-doesnt-have-permission", "permissions");
                    return true;
                }
                CoreApi.getInstance().removePlayerPermission(playerId, args[3].toLowerCase());
                CoreApi.getInstance().displayMessage((Player) sender, "permission.remove.other.done", "permissions");
                return true;
            }


        }



        return false;
    }
}
