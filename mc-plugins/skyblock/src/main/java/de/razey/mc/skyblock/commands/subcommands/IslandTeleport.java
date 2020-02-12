package de.razey.mc.skyblock.commands.subcommands;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class IslandTeleport {

    public static boolean isTp(CommandSender sender, String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        if (args.length == 0) {
            CoreApi.getInstance().displayMessage(player, "skyblock.istp.no-destination", "skyblock");
            return true;
        }

        int island = -1;

        try {
            island = Integer.parseInt(args[0]);
        } catch (Exception e) {}

        int destination;

        if (island != -1) {
            destination = island;
            if (IslandCreator.getIslandOwner(destination) == -1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.istp.island-not-exist", "skyblock");
                return true;
            }
        } else {
            int tpIslandOwner = CoreApi.getInstance().getPlayerIdFromName(args[0]);
            if (tpIslandOwner == -1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.istp.player-not-found", "skyblock");
                return true;
            }

            destination = IslandCreator.getIslandPosition(CoreApi.getInstance().getUuidFromPlayerId(tpIslandOwner));

            if (destination == -1) {
                CoreApi.getInstance().displayMessage(player, "skyblock.istp.player-has-no-island", "skyblock");
                return true;
            }

            player.teleport(IslandCreator.getIslandSpawn(destination));

            CoreApi.getInstance().displayMessage(player, "skyblock.istp.done", "skyblock");
            return true;

        }
        return true;
    }

}
