package de.razey.mc.elevators;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.elevators.Utils.RecipeLoader;
import de.razey.mc.elevators.events.BlockBreakListener;
import de.razey.mc.elevators.events.BlockPlaceListener;
import de.razey.mc.elevators.events.PlayerJumpListener;
import de.razey.mc.elevators.events.PlayerSneakListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        new RecipeLoader().registerRecipes();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockPlaceListener(),this);
        pm.registerEvents(new BlockBreakListener(),this);
        pm.registerEvents(new PlayerJumpListener(),this);
        pm.registerEvents(new PlayerSneakListener(), this);
    }

}
