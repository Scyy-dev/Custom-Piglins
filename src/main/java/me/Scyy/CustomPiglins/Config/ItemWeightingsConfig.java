package me.Scyy.CustomPiglins.Config;

import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemWeightingsConfig extends ConfigFile {

    public ItemWeightingsConfig(Plugin plugin) {
        super(plugin, "items.yml");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        // empty the piglin array
        plugin.getGenerator().getItemWeighting().clear();

        // Fill with the config items
        plugin.getGenerator().getItemWeighting().addAll(this.loadWeightings());
    }

    public ArrayList<PiglinItem> loadWeightings() {

        boolean sendWarning = false;

        int itemCount = config.getList("items").size();

        ArrayList<PiglinItem> weightings = new ArrayList<>(itemCount);

        for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {

            int weight = config.getInt("items." + itemIndex + ".weight", -1);
            ItemStack item = config.getItemStack("items." + itemIndex + ".item");
            int minAmount = config.getInt("items." + itemIndex + ".min", -1);
            int maxAmount = config.getInt("items." + itemIndex + ".max", -1);

            if (weight == -1 || item == null || minAmount == -1 || maxAmount == -1) {
                sendWarning = true;
                break;
            }

            for (int weightCounter = 0; weightCounter < weight; weightCounter++) {

                weightings.add(new PiglinItem(item, minAmount, maxAmount));

            }

        }

        if (sendWarning) {
            plugin.getLogger().warning("Could not retrieve saved items! Default drops loaded");
            return new ArrayList<>();
        }

        return weightings;

    }
}
