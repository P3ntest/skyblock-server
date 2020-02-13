package de.razey.mc.elevators.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(Material m) {
        item = new ItemStack(m);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder(Material m, short subID) {
        item = new ItemStack(m, 1, subID);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }
}