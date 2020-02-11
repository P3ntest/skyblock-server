package me.p3ntest.permissions;

import me.p3ntest.permissions.commands.PermissionsCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        this.getCommand("permissions").setExecutor(new PermissionsCommand());
    }

}