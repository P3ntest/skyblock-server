package de.razey.mc.skyblock.commands;

import de.razey.mc.skyblock.commands.subcommands.IslandTeleport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SkyBlockIsTpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        IslandTeleport.isTp(commandSender, strings);
        return true;
    }
}
