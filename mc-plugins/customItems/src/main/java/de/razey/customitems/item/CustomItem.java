package de.razey.customitems.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public interface CustomItem {

    String getDisplayName();

    default List<String> getLore() {
        return new ArrayList<String>();
    }

    boolean isEnchanted();

    Material getMaterial();

    default ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getDisplayName());
        meta.setLore(getLore());
        if (isEnchanted()) {
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        item.setItemMeta(meta);
        return item;
    }

}
