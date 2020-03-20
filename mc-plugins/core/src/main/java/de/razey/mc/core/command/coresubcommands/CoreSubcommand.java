package de.razey.mc.core.command.coresubcommands;

import org.bukkit.command.CommandSender;

public interface CoreSubcommand {

    void execute(CommandSender sender, String[] args);

}
