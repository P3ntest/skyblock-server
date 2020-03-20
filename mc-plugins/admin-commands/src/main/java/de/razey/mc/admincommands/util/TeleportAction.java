package de.razey.mc.admincommands.util;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class TeleportAction {

    public static HashMap<Player, TeleportAction> requests = new HashMap<>();

    private Player toTp;
    private Player locationPlayer;

    public TeleportAction(Player toTp, Player locationPlayer) {
        this.toTp = toTp;
        this.locationPlayer = locationPlayer;
    }

    public void execute() {
        this.toTp.teleport(this.locationPlayer);
    }

}
