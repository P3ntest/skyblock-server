package de.razey.mc.elevators.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import de.razey.mc.elevators.Utils.data;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJumpListener implements Listener {

    @EventHandler
    public void onJump(PlayerJumpEvent e){
        Player p = e.getPlayer();
        if(p.getLocation().getBlock()==null) { //Die NBT abfrage hinzufügen!
            int elevatorHigh = p.getLocation().getBlockY();
            int i = elevatorHigh+2;
            while (data.maxElevatorHigh != elevatorHigh){
                Location loc = p.getLocation();
                loc.setY(elevatorHigh);
                if(loc.getBlock() == null){ //Die NBT abfrage hinzufügen!
                    p.teleport(loc);
                    //Teleportiert
                }
                i++;
            }
        }
    }
}
