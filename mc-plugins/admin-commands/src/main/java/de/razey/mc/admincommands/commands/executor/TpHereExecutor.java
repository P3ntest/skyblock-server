package de.razey.mc.admincommands.commands.executor;

import de.razey.mc.admincommands.util.TeleportAction;
import de.razey.mc.core.api.CoreApi;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpHereExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            CoreApi.getInstance().displayMessage(((Player) sender), "no-player", null);
            return true;
        }

        if (sender.hasPermission("minecraft.command.tp")) {
            Player pp = Bukkit.getPlayer(args[0]);
            if (pp != null) {
                pp.teleport(((Player) sender).getLocation());
            }else {
                CoreApi.getInstance().displayMessage(((Player) sender), "player-not-found", null);
            }
            return true;
        }

        Player player = (Player) sender;

        Player other = Bukkit.getPlayer(args[0]);

        if (other == null) {
            CoreApi.getInstance().displayMessage(((Player) sender), "player-not-found", null);
            return true;
        }

        if (TeleportAction.requests.containsKey(player)) {
            TeleportAction.requests.remove(player);
        }



        return false;
    }
}
