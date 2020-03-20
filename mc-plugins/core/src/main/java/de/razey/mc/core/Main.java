package de.razey.mc.core;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.core.command.CoreCommandExecutor;
import de.razey.mc.core.events.CorePlayerDisconnectEvent;
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

        api = new CoreApi(this);

        this.getServer().getPluginCommand("core").setExecutor(new CoreCommandExecutor());

        this.getServer().getPluginManager().registerEvents(new CorePlayerEnterEvents(), this);
        this.getServer().getPluginManager().registerEvents(new CorePlayerDisconnectEvent(), this);

        System.out.println("Server Core enabled.");
    }

    public void onDisable() {
        System.out.println("Server Core disabled.");
        api.end();
    }

}
