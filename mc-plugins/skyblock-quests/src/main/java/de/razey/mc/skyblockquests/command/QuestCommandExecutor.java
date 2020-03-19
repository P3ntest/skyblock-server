package de.razey.mc.skyblockquests.command;

import de.razey.mc.skyblockquests.world.QuestWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ((Player) sender).teleport(QuestWorld.all.get(0).getSpawn());
        return false;
    }
}
