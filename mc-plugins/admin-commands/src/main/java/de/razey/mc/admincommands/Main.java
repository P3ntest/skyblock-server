package de.razey.mc.admincommands;

import de.razey.mc.admincommands.commands.executor.CreativeExecuter;
import de.razey.mc.admincommands.commands.executor.InvSeeExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        this.getServer().getPluginCommand("invsee").setExecutor(new InvSeeExecutor());
        this.getServer().getPluginCommand("c").setExecutor(new CreativeExecuter());
    }

}
