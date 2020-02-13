package de.razey.mc.trading;

import de.razey.mc.trading.commands.TradingCommand;
import de.razey.mc.trading.events.PlayerInventoryEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        this.getCommand("trade").setExecutor(new TradingCommand());

        this.getServer().getPluginManager().registerEvents(new PlayerInventoryEvents(), this);
    }

}
