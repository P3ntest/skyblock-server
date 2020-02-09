package de.razey.mc.skyblock.commands;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyblockIslandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (IslandCreator.getIslandPosition(player.getUniqueId().toString()) == -1) {
            IslandCreator.createIslandForPlayer(player);
            return true;
        }

        if (args.length == 0) {
            player.teleport(IslandCreator.getIslandSpawn(IslandCreator.getIslandPosition(player.getUniqueId().toString())));
            return true;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("rm")) {
                if (args.length == 1) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.delete.confirm", "skyblock");
                    return true;
                }
                if (args[1].equalsIgnoreCase(   "confirm")) {
                    IslandCreator.eraseIsland(player);
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.delete.done", "skyblock");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("sethome")) {
                if (!player.getWorld().equals(Bukkit.getWorld("islands"))) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.sethome.wrong-world", "skyblock");
                    return true;
                }
                if (!IslandCreator.isOnOwnIsland(player)) {
                    CoreApi.getInstance().displayMessage(player, "skyblock.island.sethome.wrong-island", "skyblock");
                    return true;
                }

                IslandCreator.setIslandSpawn(IslandCreator.getIslandOfLocation(player.getLocation()), player.getLocation());
                CoreApi.getInstance().displayMessage(player, "skyblock.island.sethome.done", "skyblock");
                return true;
            }

            return true;
        }

        sender.sendMessage("Befehl nicht gefunden.");
        return false;
    }
}
