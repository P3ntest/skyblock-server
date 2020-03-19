package de.razey.mc.skyblockquests.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface NPC {

    List<NPC> all = new ArrayList<>();

    void spawnNpc();

    void removeNpc();

    void clickedOn(Player player);

    Location getLocation();

    void playerEnterNPCChunkEvent(Player player);
}
