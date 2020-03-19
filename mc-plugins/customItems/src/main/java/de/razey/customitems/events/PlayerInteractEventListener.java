package de.razey.customitems.events;

import de.razey.customitems.item.BottomlessWaterbucket;
import de.razey.customitems.item.SimpleWateringCan;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerInteractEventListener implements Listener {

    public static List<Player> onWateringCooldown = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!event.isCancelled()) {
                if (event.getPlayer().getInventory().getItem(event.getHand()) != null &&
                        event.getPlayer().getInventory().getItem(event.getHand()).
                                getItemMeta() != null &&
                        event.getPlayer().getInventory().getItem(event.getHand()).
                                getItemMeta().getItemFlags() != null &&
                        event.getPlayer().getInventory().getItem(event.getHand()).
                                getItemMeta().getItemFlags().contains(ItemFlag.HIDE_PLACED_ON)) {
                    if (event.getPlayer().getInventory().getItem(event.getHand()).
                            getItemMeta().getDisplayName().equalsIgnoreCase(new SimpleWateringCan().getDisplayName())) {
                        if (onWateringCooldown.contains(event.getPlayer()))
                            return;
                        Block currCheckBlock = event.getClickedBlock();
                        int farmlandLevel = -1;
                        for (int i = 0; i < 5; i++) {
                            if (currCheckBlock.getType() == Material.FARMLAND) {
                                farmlandLevel = currCheckBlock.getLocation().getBlockY();
                                break;
                            }
                            currCheckBlock = currCheckBlock.getRelative(BlockFace.DOWN);
                        }

                        Random random = new Random();

                        if (farmlandLevel != -1) {
                            onWateringCooldown.add(event.getPlayer());
                            for (int x = -2; x < 3; x++) {
                                for (int y = -2; y < 3; y++) {
                                    Location cropLocation =
                                            new Location(event.getClickedBlock().getLocation().getWorld(),
                                                    event.getClickedBlock().getLocation().getBlockX() + x,
                                                    farmlandLevel + 1,
                                                    event.getClickedBlock().getLocation().getBlockZ() + y);
                                    if (cropLocation.getBlock() != null
                                            && cropLocation.getBlock().getBlockData() instanceof Ageable) {

                                        if (random.nextDouble() < 0.02) {
                                            Ageable ageableBlockData = (Ageable) cropLocation.getBlock().getBlockData();
                                            ageableBlockData.setAge(Math.min(ageableBlockData.getMaximumAge(), ageableBlockData.getAge() + 1));
                                            cropLocation.getBlock().setBlockData(ageableBlockData);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
