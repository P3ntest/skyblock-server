package de.razey.customitems.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class SimpleWateringCan implements CustomItem {
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&r&bGieskanne");
    }

    @Override
    public boolean isEnchanted() {
        return false;
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_HORSE_ARMOR;
    }
}
