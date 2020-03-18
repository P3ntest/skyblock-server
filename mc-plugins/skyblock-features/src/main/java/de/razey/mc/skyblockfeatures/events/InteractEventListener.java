package de.razey.mc.skyblockfeatures.events;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import de.razey.mc.skyblockfeatures.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InteractEventListener implements Listener {

    static List<Material> dyes = new ArrayList<>();
    static {
        for (Material material : Material.values()) {
            if (material.name().toLowerCase().contains("dye"))
                dyes.add(material);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.GRAVEL || event.getBlock().getType() == Material.SAND)
            if (event.getBlockAgainst().getType() == Material.SCAFFOLDING)
                if (event.getBlockAgainst().getLocation().getY() < event.getBlockPlaced().getLocation().getY())
                    event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockRemove(BlockDestroyEvent event) {
        if (event.getBlock().getType() == Material.SCAFFOLDING) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), Main.Sieve);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (!event.isCancelled()) {

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.COMPOSTER) {
                Levelled blockData = (Levelled) event.getClickedBlock().getBlockData();
                if (blockData.getLevel() >= 8) {
                    blockData.setLevel(0);
                    event.getClickedBlock().setBlockData(blockData);
                    Location spawnLocation = event.getClickedBlock().getLocation();
                    spawnLocation.setY(spawnLocation.getY() + 1);
                    Random random = new Random();

                    event.getClickedBlock().getWorld().dropItemNaturally(spawnLocation, new ItemStack(Material.DIRT, 4 + (int)(random.nextDouble() * 3)));

                    if (random.nextDouble() > 0.87)
                        event.getClickedBlock().getWorld().dropItemNaturally(spawnLocation, new ItemStack(Material.IRON_NUGGET, 1));
                    if (random.nextDouble() > 0.95)
                        event.getClickedBlock().getWorld().dropItemNaturally(spawnLocation, new ItemStack(Material.GOLD_NUGGET, 1));
                    if (random.nextDouble() > 0.7)
                        event.getClickedBlock().getWorld().dropItemNaturally(spawnLocation, new ItemStack(Material.STRING, (int)(random.nextDouble() * 3)));
                }
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SCAFFOLDING && event.getBlockFace() == BlockFace.UP) {
                if (event.getItem() != null) {
                    boolean removeItemFromHand = false;
                    if (event.getItem().getType() == Material.GRAVEL || event.getItem().getType() == Material.SAND) {
                        if (event.getPlayer().getInventory().getItem(event.getHand()).getAmount() > 1) {
                            event.getPlayer().getInventory().getItem(event.getHand()).setAmount(
                                    event.getPlayer().getInventory().getItem(event.getHand()).getAmount() - 1);
                        } else {
                            removeItemFromHand = true;
                        }

                        for (ItemStack item : getSieveDrops(event.getItem().getType())) {
                            event.getPlayer().getWorld().dropItemNaturally(
                                    event.getClickedBlock().getRelative(BlockFace.UP).getLocation(), item);
                        }

                        if (removeItemFromHand) {
                            event.getPlayer().getInventory().setItem(event.getHand(), new ItemStack(Material.AIR));
                        }

                    }
                }
            }


        }
    }

    List<ItemStack> getSieveDrops(Material sieved) {
        Random random = new Random();


        List<ItemStack> drops = new ArrayList<>();

        if (random.nextInt(100) > 50)
            return drops;

        if (sieved == Material.GRAVEL) {
            if (random.nextDouble() > 0.992)
                drops.add(new ItemStack(Material.DIAMOND, 1));

            if (random.nextDouble() > 0.992)
                drops.add(new ItemStack(Material.EMERALD, 1));

            if (random.nextDouble() > 0.7)
                drops.add(new ItemStack(Material.IRON_NUGGET, (int) (1 + random.nextDouble() * 3)));

            if (random.nextDouble() > 0.8)
                drops.add(new ItemStack(Material.GOLD_NUGGET, (int) (1 + random.nextDouble() * 3)));

            if (random.nextDouble() > 0.82)
                drops.add(new ItemStack(Material.COAL, (int) (1 + random.nextDouble() * 3)));
        }

        if (sieved == Material.SAND) {
            if (random.nextDouble() > 0.8)
                drops.add(new ItemStack(Material.GLOWSTONE_DUST, (int) (1 + random.nextDouble() * 3)));

            if (random.nextDouble() > 0.8)
                drops.add(new ItemStack(Material.GUNPOWDER, (int) (1 + random.nextDouble() * 3)));

            if (random.nextDouble() > 0.7)
                drops.add(new ItemStack(Material.REDSTONE, (int) (1 + random.nextDouble() * 3)));

            if (random.nextDouble() > 0.96)
                drops.add(new ItemStack(Material.BEETROOT_SEEDS, (int) (1 + random.nextDouble() * 3)));
            if (random.nextDouble() > 0.96)
                drops.add(new ItemStack(Material.MELON_SEEDS, (int) (1 + random.nextDouble() * 3)));
            if (random.nextDouble() > 0.96)
                drops.add(new ItemStack(Material.PUMPKIN_SEEDS, (int) (1 + random.nextDouble() * 3)));
            if (random.nextDouble() > 0.9)
                drops.add(new ItemStack(Material.WHEAT_SEEDS, (int) (1 + random.nextDouble() * 3)));

            if (random.nextDouble() > 0.7) {
                drops.add(new ItemStack(dyes.get(random.nextInt(dyes.size())), 1 + random.nextInt(3)));
            }

        }

        return drops;
    }

}
