package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Config.ItemWeightingsConfig;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CustomPiglinLootGenerator {

    private Map<Integer, PiglinItem> piglinItems;

    private final ArrayList<Integer> rawWeightings;

    private final Random random;

    public CustomPiglinLootGenerator(Plugin plugin) {

        ItemWeightingsConfig weightingsConfig = plugin.getConfigFileHandler().getItemWeightingsConfig();
        this.piglinItems = weightingsConfig.loadWeightings();
        this.random = new Random();
        this.rawWeightings = new ArrayList<>();

        for (PiglinItem piglinItem : piglinItems.values()) {

            for (int i = 0; i < piglinItem.getWeight(); i++) {

                rawWeightings.add(piglinItem.getItemID());

            }

        }

    }

    public ItemStack generateItem() {

        // Get a random value from the array
        int randomIndex = random.nextInt(piglinItems.size());

        // Get the random value in the array
        int piglinItemID = rawWeightings.get(randomIndex);
        PiglinItem piglinItem = piglinItems.get(piglinItemID);

        // Get the ItemStack from the array
        ItemStack item = piglinItems.get(randomIndex).getItem();

        // Check if the max amount is the same as min amount
        int randomAmount = 1;
        if (piglinItem.getMaxAmount() == piglinItem.getMinAmount()) randomAmount = piglinItem.getMinAmount();
        else randomAmount = random.nextInt(piglinItem.getMaxAmount() - piglinItem.getMinAmount()) + piglinItem.getMinAmount();

        // Assign the random amount
        item.setAmount(randomAmount);

        // Return the item
        return item;

    }

    public Collection<PiglinItem> getPiglinItems() {
        return piglinItems.values();
    }

    public void updatePiglinItems(Map<Integer, PiglinItem> newItems) {

        // Replace the map
        piglinItems = newItems;

        // Empty the rawWeightings array

        // Iterate over the new items and update the weighting
        for (PiglinItem piglinItem : piglinItems.values()) {

            for (int weight = 0; weight < piglinItem.getWeight(); weight++) {

                rawWeightings.add(piglinItem.getItemID());

            }

        }

    }
}
