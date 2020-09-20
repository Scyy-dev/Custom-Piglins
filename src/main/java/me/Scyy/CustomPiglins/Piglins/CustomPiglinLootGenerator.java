package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Config.ItemWeightingsConfig;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Material;
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

        // Check if there are any items available to drop
        if (rawWeightings.size() == 0) return new ItemStack(Material.AIR, 1);

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

    public void addPiglinItem(ItemStack item, int weight, int minAmount, int maxAmount) {

        int highestID = 0;
        for (Integer piglinItemID : piglinItems.keySet()) {

            if (highestID <= piglinItemID) {

                highestID = piglinItemID + 1;

            }

        }

        for (int weightCounter = 0; weightCounter < weight; weightCounter++) {

            rawWeightings.add(highestID);

        }

        piglinItems.put(highestID, new PiglinItem(item, highestID, weight, minAmount, maxAmount));

    }
}
