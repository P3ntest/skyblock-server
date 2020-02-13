package de.raey.mc.trading;

import de.raey.mc.trading.commands.TradingCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        this.getCommand("trade").setExecutor(new TradingCommand());
    }

}
