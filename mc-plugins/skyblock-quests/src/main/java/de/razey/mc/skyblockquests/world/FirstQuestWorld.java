package de.razey.mc.skyblockquests.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class FirstQuestWorld implements QuestWorld {
    @Override
    public String getName() {
        return "Farmer's Island";
    }

    @Override
    public String getIdName() {
        return "qw001";
    }

    @Override
    public Location getSpawn() {
        return new Location(Bukkit.getWorld(getIdName()), 155, 68, -102);
    }
}
