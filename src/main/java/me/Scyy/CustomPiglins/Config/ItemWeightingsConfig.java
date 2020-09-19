package me.Scyy.CustomPiglins.Config;

import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemWeightingsConfig extends ConfigFile {

    public ItemWeightingsConfig(Plugin plugin) {
        super(plugin, "items.yml");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        plugin.getGenerator().updatePiglinItems(loadWeightings());
    }

    public Map<Integer, PiglinItem> loadWeightings() {

        boolean sendWarning = false;

        if (config.getList("items") == null) {

            plugin.getLogger().warning("Unable to load any items! Piglins will not drop anything!");
            return new HashMap<>();

        }

        int itemCount = config.getList("items").size();

        Map<Integer, PiglinItem> weightings = new HashMap<>();

        int itemID = 0;

        for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {

            int weight = config.getInt("items." + itemIndex + ".weight", -1);
            ItemStack item = config.getItemStack("items." + itemIndex + ".item");
            int minAmount = config.getInt("items." + itemIndex + ".min", -1);
            int maxAmount = config.getInt("items." + itemIndex + ".max", -1);

            if (weight == -1 || item == null || minAmount == -1 || maxAmount == -1) {
                sendWarning = true;
                break;
            }

            weightings.put(itemID, new PiglinItem(item, itemID, weight, minAmount, maxAmount));

            itemID++;

        }

        if (sendWarning) {
            plugin.getLogger().warning("Unable to load items for the piglins! Piglins will not drop anything!");
            return new HashMap<>();
        }

        return weightings;

    }

    public void saveGeneratorData() {

        for (PiglinItem piglinItem : plugin.getGenerator().getPiglinItems()) {

            // Get the data
            int piglinItemID = piglinItem.getItemID();
            Map<String, Object> item = piglinItem.getItem().serialize();
            int weight = piglinItem.getWeight();
            int minAmount = piglinItem.getMinAmount();
            int maxAmount = piglinItem.getMaxAmount();

            // Save the data
            config.set("items." + piglinItemID + ".item", item);
            config.set("items." + piglinItemID + ".weight", weight);
            config.set("items." + piglinItemID + ".min", minAmount);
            config.set("items." + piglinItemID + ".max", maxAmount);

        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save Custom Piglin Items!");
            e.printStackTrace();
        }

    }

}
