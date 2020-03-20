package de.razey.mc.core.command;

import de.razey.mc.core.command.coresubcommands.DmsCommand;
import de.razey.mc.core.command.coresubcommands.SqlCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.inventory.InventoryDragEvent;

public class CoreCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("core.command"))
            return true;

        if (args.length == 0) {
            sender.sendMessage("CoreApi by P3ntest loaded. v1.6.7");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "dms":
                new DmsCommand().execute(sender, args);
                break;
            case "sql":
                new SqlCommand().execute(sender, args);
                break;
            default:
                sender.sendMessage("Subcommand not found.");
        }
        return true;
    }
}
