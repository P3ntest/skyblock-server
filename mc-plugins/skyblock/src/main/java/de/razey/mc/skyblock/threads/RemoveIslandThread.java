package de.razey.mc.skyblock.threads;

import com.sk89q.worldedit.math.BlockVector3;
import de.razey.mc.skyblock.Main;
import de.razey.mc.skyblock.schematic.IslandCreator;
import org.bukkit.Material;
import org.bukkit.World;
import org.hamcrest.core.Is;

public class RemoveIslandThread extends Thread {

    public int position;
    public World world;

    @Override
    public void run() {
        IslandToRemove islandToRemove = new IslandToRemove(position);

        System.out.println("Looking through all blocks");

        int minX = (position * IslandCreator.islandPadding) - 251;
        int maxX = (position * IslandCreator.islandPadding) + 251;

        for (int x = minX; x <= maxX; x++) {
            for (int y = 0; y <= 255; y++) {
                for (int z = -251; z <= 251; z++) {
                    if (world.getBlockAt(x, y, z).getType() != Material.AIR) {
                        System.out.println("Found block");
                        islandToRemove.blocks.add(world.getBlockAt(x, y, z));
                    }
                }
            }
        }

        System.out.println("Adding block to main");
        Main.islandsToRemove.add(islandToRemove);
    }
}
