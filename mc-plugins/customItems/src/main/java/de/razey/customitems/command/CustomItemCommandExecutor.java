package de.razey.customitems.command;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class CustomItemCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("citem.admin"))
            return true;

        if (!CoreApi.getInstance().existCustomItem(args[0]))
            return true;

        ((Player) sender).getInventory().addItem(CoreApi.getInstance().getCustomItem(args[0]));

        return false;
    }
}
