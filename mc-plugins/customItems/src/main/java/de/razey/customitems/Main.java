package de.razey.customitems;

import de.razey.customitems.command.CustomItemCommandExecutor;
import de.razey.customitems.events.BucketEventsListener;
import de.razey.customitems.events.PlayerInteractEventListener;
import de.razey.customitems.item.BottomlessWaterbucket;
import de.razey.customitems.item.SimpleWateringCan;
import de.razey.mc.core.api.CoreApi;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        CoreApi.getInstance().addCustomItem("bottomlesswaterbucket", new BottomlessWaterbucket().getItem());
        CoreApi.getInstance().addCustomItem("simplewateringcan", new SimpleWateringCan().getItem());

        this.getServer().getPluginCommand("customitem").setExecutor(new CustomItemCommandExecutor());

        this.getServer().getPluginManager().registerEvents(new BucketEventsListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEventListener(), this);

        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                PlayerInteractEventListener.onWateringCooldown.clear();
            }
        }, 20, 5);
    }
}
