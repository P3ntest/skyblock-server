package de.razey.customitems.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.List;

public class BottomlessWaterbucket implements CustomItem {
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&r&b&lBottomless Waterbucket");
    }

    @Override
    public List<String> getLore() {
        return null;
    }

    @Override
    public boolean isEnchanted() {
        return true;
    }

    @Override
    public Material getMaterial() {
        return Material.WATER_BUCKET;
    }
}
