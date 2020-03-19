package de.razey.mc.skyblockquests.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;

import java.util.ArrayList;
import java.util.List;

public interface QuestWorld {

    List<QuestWorld> all = new ArrayList<>();

    default void loadWorld() {
        Bukkit.getServer().createWorld(new WorldCreator(getIdName()));
    }

    String getName();

    String getIdName();

    Location getSpawn();

}
