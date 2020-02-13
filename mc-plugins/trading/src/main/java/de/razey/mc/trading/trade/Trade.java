package de.razey.mc.trading.trade;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    static ItemStack blackGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
    static {
        ItemMeta meta = blackGlass.getItemMeta();
        meta.setDisplayName("  ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        blackGlass.setItemMeta(meta);
    }

    static ItemStack accepted = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
    static {
        ItemMeta meta = accepted.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aACCEPTED"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        accepted.setItemMeta(meta);
    }

    static ItemStack notAccepted = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
    static {
        ItemMeta meta = notAccepted.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cNOT ACCEPTED"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        notAccepted.setItemMeta(meta);
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

    private void setAcceptedInInventory(Inventory inv, TradeSide side, boolean accepted) {
        int start = (side == TradeSide.SELF) ? 0 : 5;
        int max = (side == TradeSide.SELF) ? 4 : 9;

        for (int i = start; i < max; i++) {
            inv.setItem(i, accepted ? this.accepted : notAccepted);
        }
    }

    public void tradeForceEnd() {
        p1.closeInventory();
        p2.closeInventory();
        CoreApi.getInstance().displayMessage(p1, "trade.cancel", "trading");
        CoreApi.getInstance().displayMessage(p2, "trade.cancel", "trading");
        TradingInventoryManager.trades.remove(this);
    }

    private void setupInventory(Inventory inv) {
        for (int i = 4; i < 50; i = i + 9) {
            inv.setItem(i, blackGlass);
        }
    }

    public Trade(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;

        inventory1 = Bukkit.createInventory(null, 54, "Trade mit " + p2.getName());
        setupInventory(inventory1);
        setAcceptedInInventory(inventory1, TradeSide.SELF, false);
        setAcceptedInInventory(inventory1, TradeSide.OTHER, false);

        inventory2 = Bukkit.createInventory(null, 54, "Trade mit " + p1.getName());
        setupInventory(inventory2);
        setAcceptedInInventory(inventory2, TradeSide.SELF, false);
        setAcceptedInInventory(inventory2, TradeSide.OTHER, false);

        CoreApi.getInstance().displayMessage(p1, "trade.start", "trading", p2.getName());
        CoreApi.getInstance().displayMessage(p2, "trade.start", "trading", p1.getName());

        p1.openInventory(inventory1);
        p2.openInventory(inventory2);
    }

}
