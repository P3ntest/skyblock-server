package de.razey.mc.skyblockquests.npc;

import de.razey.mc.skyblockquests.world.FirstQuestWorld;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class OldMan01 implements VanillaEntityNPC {
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
    public Villager.Type getVillagerType() {
        return Villager.Type.JUNGLE;
    }

    @Override
    public boolean getCustomNameVisible() {
        return true;
    }

    Entity spawned;

    @Override
    public void setSpawned(Entity spawned) {
        this.spawned = spawned;
    }

    @Override
    public Entity getSpawned() {
        return spawned;
    }

    @Override
    public boolean cancelClickEvent() {
        return true;
    }

    @Override
    public void clickedOn(Player player) {

    }

    @Override
    public Location getLocation() {
        return new Location(new FirstQuestWorld().getSpawn().getWorld(),  158.7f, 67.0625f, -91.980, 149, 0);
    }

    @Override
    public void playerEnterNPCChunkEvent(Player player) {

    }
}
