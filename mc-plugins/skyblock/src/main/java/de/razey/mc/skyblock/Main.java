package de.razey.mc.skyblock;

import de.razey.mc.skyblock.commands.SkyblockIslandCommand;
import de.razey.mc.skyblock.events.SkyblockPlayerInteractEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main _instance;

    public static final Location worldSpawn;

    public void onEnable() {
        try {
            getDataFolder().mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        _instance = this;

        System.out.println("Enabled Skyblock");

        this.getCommand("island").setExecutor(new SkyblockIslandCommand());

        this.getServer().getPluginManager().registerEvents(new SkyblockPlayerInteractEvent(), this);
    }

}
