package de.razey.mc.skyblock.commands;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.commands.subcommands.IslandTeleport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SkyblockIsAdminCommand implements CommandExecutor {

    public static List<Player> admin = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("is.admin")) {
            Player player = (Player) commandSender;

            if (!admin.contains(player)) {
                admin.add(player);
            } else {
                admin.remove(player);
            }

            CoreApi.getInstance().displayMessage(player, "skyblock.admin.toggle", "skyblock");
        }
        return true;
    }

}
