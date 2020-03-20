package de.razey.mc.skyblockquests.npc;

import de.razey.mc.core.api.CoreApi;
import de.razey.mc.skyblockquests.Main;
import de.razey.mc.skyblockquests.world.FirstQuestWorld;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Farmer implements VanillaEntityNPC {
    private Entity spawned = null;

    @Override
    public String getNpcName() {
        return ChatColor.translateAlternateColorCodes('&', "&eFarmer");
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.VILLAGER;
    }

    @Override
    public Villager.Profession getVillagerProfession() {
        return Villager.Profession.FARMER;
    }

    @Override
    public boolean getCustomNameVisible() {
        return true;
    }

    @Override
    public void setSpawned(Entity spawned) {
        this.spawned = spawned;
    }

    @Override
    public boolean cancelClickEvent() {
        return true;
    }

    @Override
    public Villager.Type getVillagerType() {
        return Villager.Type.SAVANNA;
    }

    @Override
    public Entity getSpawned() {
        return spawned;
    }

    private static final List<Player> wheatCooldown = new ArrayList<>();

    @Override
    public void clickedOn(Player player) {
        if (wheatCooldown.contains(player)) {
            CoreApi.getInstance().displayMessage(player, "npc.farmer.still-on-cooldown", null);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            return;
        }
        if (player.getInventory().contains(new ItemStack(Material.WHEAT, 64))) {
            player.getInventory().remove(new ItemStack(Material.WHEAT, 64));
            player.getInventory().addItem(CoreApi.getInstance().getCustomItem("simplewateringcan"));
            CoreApi.getInstance().displayMessage(player, "npc.farmer.thanks", null);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 10, 1);
            wheatCooldown.add(player);
            Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    wheatCooldown.remove(player);
                }
            }, 12000);
        } else {
            CoreApi.getInstance().displayMessage(player, "npc.farmer.get-wheat", null);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 10, 1);
        }


    }

    @Override
    public Location getLocation() {
        Location location = new Location(new FirstQuestWorld().getSpawn().getWorld(),
                -99.6, 64, -93.1, 163, 22);
        return location;
    }

    @Override
    public void playerEnterNPCChunkEvent(Player player) {

    }
}