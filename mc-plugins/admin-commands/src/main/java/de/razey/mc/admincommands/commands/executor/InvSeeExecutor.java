package de.razey.mc.admincommands.commands.executor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.Arrays;

public class InvSeeExecutor implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        if (!sender.hasPermission("ac.invsee"))
            return true;

        if (!(Arrays.asList(args).contains("-a"))) {
            if (args.length < 1) {
                sender.sendMessage("No user");
                return true;
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                ((Player) sender).openInventory(Bukkit.getPlayer(args[0]).getInventory());
                sender.sendMessage("Inventory opened");
            } else {
                sender.sendMessage("User not found");
            }
            return true;
        } else {
            sender.sendMessage("Diese Funktion ist noch nicht verfügbar");
            if (args.length < 2) {
                sender.sendMessage("No user");
                return true;
            }
            if (Bukkit.getPlayer(args[0]) != null) {

            } else {
                sender.sendMessage("User not found");
            }
        }

        return true;
    }
}
