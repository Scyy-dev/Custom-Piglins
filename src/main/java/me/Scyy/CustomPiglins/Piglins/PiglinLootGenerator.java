package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Config.PiglinItemData;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PiglinLootGenerator {

    private Map<Integer, PiglinItem> piglinItems;

    private final ArrayList<Integer> rawWeightings;

    private final Random random;

    public PiglinLootGenerator(Plugin plugin) {

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

    // Item Generators

    /**
     * Randomly chooses a piglin item and returns its associated ItemStack
     * @return the ItemStack the piglin item refers to
     */
    public ItemStack generateItem() {

        // Check if there are any items available to drop
        if (rawWeightings.size() == 0) return new ItemStack(Material.AIR, 1);

        // Get a random value from the array
        int randomIndex = random.nextInt(rawWeightings.size());

        // Get the random value in the array
        int piglinItemID = rawWeightings.get(randomIndex);
        PiglinItem piglinItem = piglinItems.get(piglinItemID);

        // Get the ItemStack from the array
        ItemStack item = piglinItems.get(piglinItemID).getItem().clone();

        // Check if the max amount is the same as min amount
        int randomAmount;
        if (piglinItem.getMaxAmount() == piglinItem.getMinAmount()) randomAmount = piglinItem.getMinAmount();
        else randomAmount = random.nextInt(piglinItem.getMaxAmount() - piglinItem.getMinAmount() + 1) + piglinItem.getMinAmount();

        // Check if the item has random durability
        if (piglinItem.hasRandomDamage() && item.getItemMeta() != null) {

            ItemMeta itemMeta = item.getItemMeta();
            Damageable damageableItem = (Damageable) itemMeta;
            damageableItem.setDamage(random.nextInt(item.getType().getMaxDurability() + 1) + 1);
            item.setItemMeta(itemMeta);

        }

        // Assign the random amount
        item.setAmount(randomAmount);

        // Return the item
        return item;

    }

    // Getters

    /**
     * Gets a piglin item from the map from an ID
     * @param piglinItemID the ID of the piglin item
     * @return the piglin item
     */
    public PiglinItem getPiglinItem(int piglinItemID) {

        return piglinItems.get(piglinItemID);

    }

    /**
     * Provides a collection of all unique piglin items. Collection is sorted by piglinItemID, with the lowest ID being first
     * @return the collection of piglin items
     */
    public Collection<PiglinItem> getPiglinItems() {

        ArrayList<PiglinItem> piglinItemArray = new ArrayList<>(piglinItems.values());

        piglinItemArray.sort(((piglinItem, piglinItem1) -> Math.min(piglinItem.getItemID(), piglinItem1.getItemID())));

        return piglinItemArray;
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

    // Updaters

    /**
     * Updates an individual piglin item
     * @param piglinItem the pigln item to be updated
     */
    public void updatePiglinItem(PiglinItem piglinItem,boolean changedWeightings) {

        piglinItems.put(piglinItem.getItemID(), piglinItem);
        if (changedWeightings) this.generateWeightings();

    }

    // Setters

    /**
     * Creates a new piglin item based off of the required data
     * @param item the ItemStack to be added
     * @param weight the chance of the item being dropped, must be > 0
     * @param minAmount minimum amount of the item to be dropped
     * @param maxAmount max amount of the item to be dropped
     */
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
     * Updates all piglin items
     * @param newItems the new piglin items to be added
     */
    public void setPiglinItems(Map<Integer, PiglinItem> newItems) {

        // Replace the map
        piglinItems = newItems;

        // Reload the weightings array
        generateWeightings();

    }

    /**
     * Removes an item from the generator and remakes the weighting array
     * @param piglinItemID the ID of the item to be removed
     */
    public void removePiglinItem(int piglinItemID) {

        // Remove the item
        piglinItems.remove(piglinItemID);

        // Regenerate the item chances
        generateWeightings();

    }

    // Method helpers

    /**
     * Empties the rawWeighting array and refills it based on the piglin item weightings in the piglin items map
     */
    private void generateWeightings() {

        // Empty the rawWeightings array
        rawWeightings.clear();

        // Iterate over the new items and update the weighting
        for (PiglinItem piglinItem : piglinItems.values()) {

            for (int weight = 0; weight < piglinItem.getWeight(); weight++) {

                rawWeightings.add(piglinItem.getItemID());

            }

        }

    }

}
