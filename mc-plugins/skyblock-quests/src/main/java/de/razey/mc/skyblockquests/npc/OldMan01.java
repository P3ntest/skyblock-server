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

public class OldMan01 implements VanillaEntityNPC {
    private Entity spawned = null;

    @Override
    public String getNpcName() {
        return ChatColor.translateAlternateColorCodes('&', "&eAlter Mann &r&f(Klick mich!)");
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.VILLAGER;
    }

    @Override
    public Villager.Profession getVillagerProfession() {
        return Villager.Profession.NITWIT;
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
        return Villager.Type.JUNGLE;
    }

    @Override
    public Entity getSpawned() {
        return spawned;
    }

    @Override
    public void clickedOn(Player player) {
        CoreApi.getInstance().displayMessage(player, "npc.oldman.01.greet", null);
    }

    @Override
    public Location getLocation() {
        Location location = new Location(new FirstQuestWorld().getSpawn().getWorld(),
                158.7, 67.0625, -91.980, 149, 0);
        return location;
    }

    @Override
    public void playerEnterNPCChunkEvent(Player player) {

    }
}
