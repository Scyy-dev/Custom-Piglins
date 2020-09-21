package me.Scyy.CustomPiglins.Config;

import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
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

        if (config.getConfigurationSection("items") == null) {

            plugin.getLogger().warning("Unable to load any items! Piglins will not drop anything!");
            return new HashMap<>();

        }

        if (config.getConfigurationSection("items").getKeys(false).size() == 0) {

            plugin.getLogger().warning("No items found in items.yml! Piglins will not drop anything!");
            return new HashMap<>();

        }

        Map<Integer, PiglinItem> weightings = new HashMap<>();

        for (String key : config.getConfigurationSection("items").getKeys(false)) {

            int weight = config.getInt("items." + key + ".weight", -1);
            int itemID = Integer.parseInt(key);
            ItemStack item = ItemStack.deserialize(config.getConfigurationSection("items." + key + ".item").getValues(true));
            int minAmount = config.getInt("items." + key + ".min", -1);
            int maxAmount = config.getInt("items." + key + ".max", -1);
            boolean hasRandomDamage = config.getBoolean("items." + key + ".hasRD", false);

            if (weight == -1 || minAmount == -1 || maxAmount == -1) {
                sendWarning = true;
                break;
            }

            weightings.put(itemID, new PiglinItem(item, itemID, weight, minAmount, maxAmount, hasRandomDamage));

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
            boolean hasRD = piglinItem.hasRandomDamage();

            // Save the data
            config.set("items." + piglinItemID + ".item", item);
            config.set("items." + piglinItemID + ".weight", weight);
            config.set("items." + piglinItemID + ".min", minAmount);
            config.set("items." + piglinItemID + ".max", maxAmount);
            config.set("items." + piglinItemID + ".hasRD", hasRD);

        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save Custom Piglin Items!");
            e.printStackTrace();
        }

    }

}
