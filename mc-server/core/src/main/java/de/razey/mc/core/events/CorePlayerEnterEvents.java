package de.razey.mc.core.events;

import de.razey.mc.core.Main;
import de.razey.mc.core.api.CoreApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CorePlayerEnterEvents implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        e.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent e) throws SQLException {
        e.getPlayer().setOp(false);
        ResultSet result =
                CoreApi.getInstance().getSql().resultStatement("SELECT username FROM users WHERE uuid='" + e.getPlayer().getUniqueId().toString() + "'");
        if (result.next()) {
            if (!result.getString(1).equalsIgnoreCase(e.getPlayer().getName())) {
                CoreApi.getInstance().updateUsername(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName());
            }
        }else {
            CoreApi.getInstance().playerFirstJoin(e.getPlayer());
        }

        PermissionAttachment permissionAttachment = e.getPlayer().addAttachment(Main._instance);
        for (String perm : CoreApi.getInstance().getPlayerPermissions(e.getPlayer().getUniqueId().toString())) {
            if (perm.equalsIgnoreCase("*")) {
                e.getPlayer().setOp(true);
            }
            permissionAttachment.setPermission(perm, true);
        }
    }

}
