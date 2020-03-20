package de.razey.mc.admincommands.commands.executor;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class FeedExecuter implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("command.feed"))
            return true;

        if (args.length == 0) {
            ((Player) sender).setHealth(((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            return true;
        }

        if (!sender.hasPermission("command.feed.other"))
            return true;

        Player toFeed = Bukkit.getPlayer(args[0]);
        if (toFeed == null) {
            CoreApi.getInstance().displayMessage(((Player) sender), "player-not-found", null);
            return true;
        }

        toFeed.setHealth(toFeed.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        return true;
    }
}
