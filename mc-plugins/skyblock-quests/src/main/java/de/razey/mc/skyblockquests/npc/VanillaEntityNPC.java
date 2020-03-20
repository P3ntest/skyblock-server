package de.razey.mc.skyblockquests.npc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;

import javax.swing.text.Style;
import java.util.Collection;
import java.util.List;

public interface VanillaEntityNPC extends NPC {

    String getNpcName();

    EntityType getEntityType();

    default Villager.Profession getVillagerProfession() {return Villager.Profession.BUTCHER;}

    default Villager.Type getVillagerType() {return Villager.Type.PLAINS;}

    boolean getCustomNameVisible();

    default void postSpawnAction(Entity spawned) {}

    void setSpawned(Entity spawned);
    Entity getSpawned();

    @Override
    default void removeNpc() {
        getSpawned().remove();
    }

    boolean cancelClickEvent();

    @Override
    default void spawnNpc() {
        Collection<Entity> entities = getLocation().getNearbyEntities(2, 2, 2);
        for (Entity entity : entities) {
            if (entity.getName().equalsIgnoreCase(getNpcName()) || entity.getCustomName().equalsIgnoreCase(getNpcName())) {
                entity.remove();
            }
        }

        Entity spawned = getLocation().getWorld().spawnEntity(getLocation(), getEntityType());
        if (getEntityType() == EntityType.VILLAGER) {
            ((Villager) spawned).setProfession(getVillagerProfession());
            ((Villager) spawned).setVillagerType(getVillagerType());
        }

        spawned.setCustomNameVisible(getCustomNameVisible());
        spawned.setCustomName(getNpcName());
        spawned.setInvulnerable(true);
        spawned.setGravity(false);

        spawned.setFireTicks(0);

        ((LivingEntity) spawned).setAI(false);

        setSpawned(spawned);

        postSpawnAction(spawned);
    }
}
