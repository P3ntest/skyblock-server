package de.razey.mc.core.command.coresubcommands;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.command.CommandSender;

public class DmsCommand implements CoreSubcommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("Nachricht setzen: /core dms <message_id> set <value> [language]");
            sender.sendMessage("Nachricht auslesen: /core dms <message_id> [language]");
            return;
        }

        if (args.length == 2) {
            String message = CoreApi.getInstance().getMessage(args[1], "de");
            if (message != null) {
                sender.sendMessage("[de][" + args[1] + "]:");
                sender.sendMessage(message);
                return;
            } else {
                sender.sendMessage("[de][" + args[1] + "] existiert nicht.");
                return;
            }
        }

        if (args.length == 3) {
            if (args[2].equalsIgnoreCase("set")) {
                sender.sendMessage("/core dms <message_id> set <value> [language]");
                return;
            }
            String message = CoreApi.getInstance().getMessage(args[1], args[2]);
            if (message != null) {
                sender.sendMessage("[" + args[2] + "][" + args[1] + "]:");
                sender.sendMessage(message);
                return;
            } else {
                sender.sendMessage("[" + args[2] + "][" + args[1] + "] existiert nicht.");
                return;
            }
        }

        CoreApi.getInstance().setMessage(args[1], args[3], (args.length >= 5) ? args[4] : "de");
        sender.sendMessage("Update.");
    }
}
