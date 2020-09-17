package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class CustomPiglinLootGenerator {

    private final ArrayList<PiglinItem> itemWeighting;

    private final Plugin plugin;

    private final Random random;

    public CustomPiglinLootGenerator(Plugin plugin) {

        this.plugin = plugin;
        this.itemWeighting = plugin.getConfigFileHandler().loadWeightings();
        this.random = new Random();

    }

    public ItemStack generateItem() {

        // Get a random value from the array
        int randomIndex = random.nextInt(itemWeighting.size());

        // Get the random value in the array
        PiglinItem piglinItem = itemWeighting.get(randomIndex);

        // Get the ItemStack from the array
        ItemStack item = itemWeighting.get(randomIndex).getItem();

        // Calculate the random quantity
        int randomAmount = random.nextInt(piglinItem.getMaxAmount() - piglinItem.getMinAmount()) + piglinItem.getMinAmount();

        // Assign the random amount
        item.setAmount(randomAmount);

        // Return the item
        return item;

    }

    public ArrayList<PiglinItem> getItemWeighting() {
        return itemWeighting;
    }

}
