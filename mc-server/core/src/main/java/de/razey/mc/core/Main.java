package de.razey.mc.core;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.core.events.CorePlayerEnterEvents;
import de.razey.mc.core.sql.CoreSql;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main _instance;

    public CoreSql sql;

    private CoreApi api;

    public void onEnable() {
        _instance = this;
        saveDefaultConfig();
        System.out.println("Server Core enabled.");

        api = new CoreApi(this);

        this.getServer().getPluginManager().registerEvents(new CorePlayerEnterEvents(), this);
    }

    public void onDisable() {
        System.out.println("Server Core disabled.");
        api.end();
    }

}
