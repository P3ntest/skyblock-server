package de.razey.mc.skyblock;

import de.razey.mc.skyblock.commands.*;
import de.razey.mc.skyblock.commands.subcommands.SkyblockPayCommand;
import de.razey.mc.skyblock.events.*;
import de.razey.mc.skyblock.threads.IslandToRemove;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main _instance;

    public static Location worldSpawn() {
        return new Location(Bukkit.getWorld("world"), 0.5f, 101.0f, 0.5f);
    }

    public static List<IslandToRemove> islandsToRemove = new ArrayList<>();

    public void onEnable() {

        getServer().createWorld(new WorldCreator("islands"));

        try {
            getDataFolder().mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        _instance = this;

        this.getCommand("island").setExecutor(new SkyblockIslandCommand());
        this.getCommand("balance").setExecutor(new SkyblockBalanceCommand());
        this.getCommand("istp").setExecutor(new SkyBlockIsTpCommand());
        this.getCommand("isadmin").setExecutor(new SkyblockIsAdminCommand());
        this.getCommand("spawn").setExecutor(new SkyblockSpawnCommand());
        this.getCommand("pay").setExecutor(new SkyblockPayCommand());

        this.getServer().getPluginManager().registerEvents(new SkyblockPlayerInteractEvent(), this);
        this.getServer().getPluginManager().registerEvents(new SkyblockBlockChangeEvent(), this);
        this.getServer().getPluginManager().registerEvents(new SkyblockBucketEvent(), this);
        this.getServer().getPluginManager().registerEvents(new SkyblockPlayerMoveEvent(), this);
        this.getServer().getPluginManager().registerEvents(new SkyblockEntityDamageEvent(), this);

        System.out.println("Enabled Skyblock");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (islandsToRemove.size() > 0) {
                    System.out.println("Detected island remove");
                    for (int i = 0; i < 8000; i++) {
                        if (islandsToRemove.size() > 0) {
                            Block b = islandsToRemove.get(0).blocks.get(0);
                            b.setType(Material.AIR);
                            islandsToRemove.get(0).blocks.remove(b);
                            if (islandsToRemove.get(0).blocks.size() < 1) {
                                islandsToRemove.remove(0);
                                System.out.println("Removed islandToRemove");
                            }
                        }
                    }
                }
            }
        }, 1, 1);
    }

}
