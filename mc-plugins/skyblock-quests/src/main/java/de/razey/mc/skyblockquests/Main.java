package de.razey.mc.skyblockquests;

import de.razey.mc.skyblockquests.command.QuestCommandExecutor;
import de.razey.mc.skyblockquests.events.EntityDamageEventsListener;
import de.razey.mc.skyblockquests.events.PlayerInteractEventListener;
import de.razey.mc.skyblockquests.npc.Farmer;
import de.razey.mc.skyblockquests.npc.NPC;
import de.razey.mc.skyblockquests.npc.VanillaEntityNPC;
import de.razey.mc.skyblockquests.world.FirstQuestWorld;
import de.razey.mc.skyblockquests.world.QuestWorld;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        this.instance = this;

        this.getServer().getPluginCommand("questworld").setExecutor(new QuestCommandExecutor());

        this.getServer().getPluginManager().registerEvents(new EntityDamageEventsListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEventListener(), this);

        QuestWorld.all.add(new FirstQuestWorld());

        NPC.all.add(new Farmer());

        for (QuestWorld questWorld : QuestWorld.all) {
            questWorld.loadWorld();
        }

        for (NPC npc : NPC.all) {
            npc.spawnNpc();
        }
    }
}
