package de.raey.mc.trading.trade;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Trade {

    Player p1;
    Player p2;

    Inventory inventory1;
    Inventory inventory2;

    static ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    static {
        ItemMeta meta = blackGlass.getItemMeta();
        meta.setDisplayName("");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        blackGlass.setItemMeta(meta);
    }

    public boolean isPlayerInTrade(Player player) {
        if (p1 == player) {
            return true;
        }
        if (p2 == player) {
            return true;
        }
        return false;
    }

    enum TradeSide {
        SELF,
        OTHER;
    }

    private void setAcceptedInInventory(Inventory inv, TradeSide side) {
        int start = (side == TradeSide.SELF) ? 0 : 5;
        int max = (side == TradeSide.SELF) ? 4 : 9;
    }

    private void setupInventory(Inventory inv) {
        for (int i = 4; i < 50; i = i + 9) {
            inv.setItem(i, blackGlass);
        }
    }

    public Trade(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;

        inventory1 = Bukkit.createInventory(null, 45);
        setupInventory(inventory1);

        inventory2 = Bukkit.createInventory(null, 45);
        setupInventory(inventory2);



    }

}
