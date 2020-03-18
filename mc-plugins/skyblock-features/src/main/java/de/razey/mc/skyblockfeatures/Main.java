package de.razey.mc.skyblockfeatures;

import de.razey.mc.skyblockfeatures.events.EntitySpawnListener;
import de.razey.mc.skyblockfeatures.events.InteractEventListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static ItemStack Sieve = new ItemStack(Material.SCAFFOLDING, 1);
    static {
        ItemMeta meta = Sieve.getItemMeta();
        meta.setDisplayName("§rSieve");
        Sieve.setItemMeta(meta);
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new InteractEventListener(), this);
        getServer().getPluginManager().registerEvents(new EntitySpawnListener(), this);

        NamespacedKey sieveKey = new NamespacedKey(this, "sieve");
        ShapedRecipe recipe = new ShapedRecipe(sieveKey, Sieve);
        recipe.shape("SCS", "S S", "S S");
        recipe.setIngredient('S', new ItemStack(Material.STICK));
        recipe.setIngredient('C', new ItemStack(Material.COBWEB));
        Bukkit.addRecipe(recipe);

        NamespacedKey cobwebKey = new NamespacedKey(this, "cobweb_rec");
        ShapedRecipe cobwebRecipe = new ShapedRecipe(cobwebKey, new ItemStack(Material.COBWEB, 1));
        cobwebRecipe.shape("SSS", "SSS", "SSS");
        cobwebRecipe.setIngredient('S', new ItemStack(Material.STRING));
        Bukkit.addRecipe(cobwebRecipe);

        NamespacedKey gravelKey = new NamespacedKey(this, "gravel_rec");
        ShapelessRecipe gravelRecipe = new ShapelessRecipe(gravelKey, new ItemStack(Material.GRAVEL, 2));
        gravelRecipe.addIngredient(new ItemStack(Material.DIRT));
        gravelRecipe.addIngredient(new ItemStack(Material.COBBLESTONE));
        Bukkit.addRecipe(gravelRecipe);

        NamespacedKey sandKey = new NamespacedKey(this, "sand_rec");
        ShapelessRecipe sandRecipe = new ShapelessRecipe(sandKey, new ItemStack(Material.SAND, 3));
        sandRecipe.addIngredient(4, new ItemStack(Material.GRAVEL));
        Bukkit.addRecipe(sandRecipe);
    }

}
