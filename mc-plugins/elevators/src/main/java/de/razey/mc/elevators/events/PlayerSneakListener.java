package de.razey.mc.elevators.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerSneakListener implements Listener {

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if(!e.isSneaking())return;
        Player p = e.getPlayer();
        if(p.getLocation().getBlock()==null) { //Die NBT abfrage hinzufügen!
            int elevatorHigh = p.getLocation().getBlockY();
            int i = elevatorHigh-2;
            while (0 != elevatorHigh){
                Location loc = p.getLocation();
                loc.setY(elevatorHigh);
                if(loc.getBlock() == null){ //Die NBT abfrage hinzufügen!
                    p.teleport(loc);
                    //Teleportiert
                }
                i--;
            }
        }
    }
}
