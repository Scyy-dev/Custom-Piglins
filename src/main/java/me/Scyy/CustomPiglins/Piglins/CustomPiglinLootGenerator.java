package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Config.PiglinItemData;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CustomPiglinLootGenerator {

    private Map<Integer, PiglinItem> piglinItems;

    private final ArrayList<Integer> rawWeightings;

    private final Random random;

    public CustomPiglinLootGenerator(Plugin plugin) {

        PiglinItemData piglinItemData = plugin.getConfigFileHandler().getPiglinItemDataConfig();
        this.piglinItems = piglinItemData.loadPiglinItems();
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
        int randomIndex = random.nextInt(rawWeightings.size());

        // Get the random value in the array
        int piglinItemID = rawWeightings.get(randomIndex);
        PiglinItem piglinItem = piglinItems.get(piglinItemID);

        // Get the ItemStack from the array
        ItemStack item = piglinItems.get(piglinItemID).getItem();

        // Check if the max amount is the same as min amount
        int randomAmount;
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
        rawWeightings.clear();

        // Iterate over the new items and update the weighting
        for (PiglinItem piglinItem : piglinItems.values()) {

            for (int weight = 0; weight < piglinItem.getWeight(); weight++) {

                rawWeightings.add(piglinItem.getItemID());

            }

        }

    }

    public void updatePiglinItem(PiglinItem piglinItem) {

        piglinItems.put(piglinItem.getItemID(), piglinItem);
        this.updatePiglinItems(piglinItems);

    }

    public void addPiglinItem(ItemStack item, int weight, int minAmount, int maxAmount, boolean hasRandomDamage) {

        int highestID = 0;
        for (Integer piglinItemID : piglinItems.keySet()) {

            if (highestID <= piglinItemID) {

                highestID = piglinItemID + 1;

            }

        }

        for (int weightCounter = 0; weightCounter < weight; weightCounter++) {

            rawWeightings.add(highestID);

        }

        piglinItems.put(highestID, new PiglinItem(item, highestID, weight, minAmount, maxAmount, hasRandomDamage));

    }

    /**
     * Returns the chance of a particular piglin item from dropping. Throws an IllegalArgumentException if the
     * Piglin item has an weight equal or below 0
     * @param item the item to be tested
     * @return the chance of the item dropping, a value between 0 and 1
     */
    public double getChance(PiglinItem item) {

        if (item.getWeight() <= 0) throw new IllegalArgumentException("Piglin item must have positive weight");

        return (double) item.getWeight() / (double) rawWeightings.size();

    }
}
