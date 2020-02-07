package de.razey.mc.core.events;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CorePlayerDisconnectEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        CoreApi.getInstance().updateLastOnlineTime(e.getPlayer().getUniqueId().toString());
    }

}
