package me.Scyy.CustomPiglins.Config;

import me.Scyy.CustomPiglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;

import java.util.ArrayList;

public class ConfigFileHandler {

    private final Plugin plugin;

    private final ItemWeightingsConfig itemWeightingsConfig;

    private final PlayerMessenger playerMessenger;

    private final DefaultConfig defaultConfig;

    public ConfigFileHandler(Plugin plugin) {

        this.plugin = plugin;

        this.itemWeightingsConfig = new ItemWeightingsConfig(plugin);

        this.playerMessenger = new PlayerMessenger(plugin);

        this.defaultConfig = new DefaultConfig(plugin);

    }

    public void reloadConfigs() {

        itemWeightingsConfig.reloadConfig();
        playerMessenger.reloadConfig();
        defaultConfig.reloadConfig();

    }

    public ArrayList<PiglinItem> loadWeightings() {

        return itemWeightingsConfig.loadWeightings();

    }

    public ItemWeightingsConfig getItemWeightingsConfig() {
        return itemWeightingsConfig;
    }

    public PlayerMessenger getPlayerMessenger() {
        return playerMessenger;
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }
}
