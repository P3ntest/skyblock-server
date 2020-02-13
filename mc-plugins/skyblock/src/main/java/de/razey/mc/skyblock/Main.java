package de.razey.mc.skyblock;

import de.razey.mc.skyblock.commands.SkyBlockIsTpCommand;
import de.razey.mc.skyblock.commands.SkyblockBalanceCommand;
import de.razey.mc.skyblock.commands.SkyblockIslandCommand;
import de.razey.mc.skyblock.events.SkyblockBlockChangeEvent;
import de.razey.mc.skyblock.events.SkyblockBucketEvent;
import de.razey.mc.skyblock.events.SkyblockPlayerInteractEvent;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main _instance;

    public static Location worldSpawn() {
        return new Location(Bukkit.getWorld("world"), 0.5f, 101.0f, 0.5f);
    }

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

        this.getServer().getPluginManager().registerEvents(new SkyblockPlayerInteractEvent(), this);
        this.getServer().getPluginManager().registerEvents(new SkyblockBlockChangeEvent(), this);
        this.getServer().getPluginManager().registerEvents(new SkyblockBucketEvent(), this);


        System.out.println("Enabled Skyblock");
    }

}
