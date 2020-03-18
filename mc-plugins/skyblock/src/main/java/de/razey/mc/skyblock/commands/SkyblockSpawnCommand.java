package de.razey.mc.skyblock.commands;

import de.razey.mc.skyblock.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyblockSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        player.teleport(Main.worldSpawn());

        return false;
    }
}
