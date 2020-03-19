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

    boolean p1accept;
    boolean p2accept;

    Inventory tradeinv1;
    Inventory tradeinv2;

    static ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
    static {
        ItemMeta meta = blackGlass.getItemMeta();
        meta.setDisplayName("  ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        blackGlass.setItemMeta(meta);
    }

    static ItemStack accepted = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
    static {
        ItemMeta meta = accepted.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aACCEPTED"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        accepted.setItemMeta(meta);
    }

    static ItemStack notAccepted = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
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

    public static boolean isInvTradeInv(Inventory inv){
        if (TradingInventoryManager.trades.contains(inv)){
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

    private void setAcceptedInInventory() {
        for (int i = 0; i < 4; i++) {
            tradeinv1.setItem(i, p1accept ? this.accepted : notAccepted);
        }
        for (int i = 0; i < 4; i++) {
            tradeinv2.setItem(i, p2accept ? this.accepted : notAccepted);
        }
    }

    public void accept(Player p){
        if(p == p1){
            p1accept = true;
        }
        else if(p == p2){
            p2accept = true;
        }
        setAcceptedInInventory();
        if (p1accept && p2accept) this.TradFinish();
    }

    private void TradFinish(){
        p1.closeInventory();
        p2.closeInventory();
        CoreApi.getInstance().displayMessage(p1, "trade.successful", "trading");
        CoreApi.getInstance().displayMessage(p2, "trade.successful", "trading");
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
        this.p1accept = false;
        this.p2accept = false;

        tradeinv1 = Bukkit.createInventory(null, 54, "Trade mit " + p2.getName());
        setupInventory(tradeinv1);

        tradeinv2 = Bukkit.createInventory(null, 54, "Trade mit " + p1.getName());
        setupInventory(tradeinv2);
        setAcceptedInInventory();

        CoreApi.getInstance().displayMessage(p1, "trade.start", "trading", p2.getName());
        CoreApi.getInstance().displayMessage(p2, "trade.start", "trading", p1.getName());

        p1.openInventory(tradeinv1);
        p2.openInventory(tradeinv2);


    }

    public static boolean notAllowedToClick(int tile){
        for (int i = 4; i < 50; i = i + 9) {
            for (int ii = i; ii < i + 4; i++)
                if(tile <= 9 || tile == ii){
                    return true;
                }
        }
        return false;
    }

    public static Trade getTradeFromPlayer(Player p){
        int i = 0;
        while (true){
            i++;
            if(TradingInventoryManager.trades.get(i).p1 == p || TradingInventoryManager.trades.get(i).p2 == p){
                return TradingInventoryManager.trades.get(i);
            }
        }
    }

}
