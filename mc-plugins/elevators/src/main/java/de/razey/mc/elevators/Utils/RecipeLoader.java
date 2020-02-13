package de.razey.mc.elevators.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeLoader {

    public void registerRecipes() {
        ShapelessRecipe elevatorRecipe = new ShapelessRecipe(new ItemBuilder(Material.DAYLIGHT_DETECTOR).setDisplayName(data.elevatorName).build());
        elevatorRecipe.addIngredient(Material.DIAMOND);
        elevatorRecipe.addIngredient(Material.DIAMOND_BLOCK);
        Bukkit.addRecipe(elevatorRecipe);
    }
}
