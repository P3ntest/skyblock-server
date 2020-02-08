package de.razey.mc.skyblock;

import de.razey.mc.skyblock.commands.SkyblockIslandCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main _instance;

    public void onEnable() {
        _instance = this;

        System.out.println("Enabled Skyblock");

        this.getCommand("island").setExecutor(new SkyblockIslandCommand());
    }

}
