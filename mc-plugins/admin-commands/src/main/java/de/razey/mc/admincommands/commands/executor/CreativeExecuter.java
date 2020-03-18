package de.razey.mc.admincommands.commands.executor;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class CreativeExecuter implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("minecraft.command.gamemode"))
            return true;
        if (!(sender instanceof Player))
            return true;

        ((Player) sender).setGameMode(GameMode.CREATIVE);


        return true;
    }
}
