package me.Scyy.CustomPiglins.Config;

import me.Scyy.CustomPiglins.Plugin;

public class ConfigFileHandler {

    private final Plugin plugin;

    private final PiglinItemData piglinItemData;

    private final PlayerMessenger playerMessenger;

    private final DefaultConfig defaultConfig;

    public ConfigFileHandler(Plugin plugin) {

        this.plugin = plugin;

        this.piglinItemData = new PiglinItemData(plugin);

        this.playerMessenger = new PlayerMessenger(plugin);

        this.defaultConfig = new DefaultConfig(plugin);

    }

    public void reloadConfigs() {

        piglinItemData.reloadConfig();
        playerMessenger.reloadConfig();
        defaultConfig.reloadConfig();

    }

    public PiglinItemData getPiglinItemDataConfig() {
        return piglinItemData;
    }

    public PlayerMessenger getPlayerMessenger() {
        return playerMessenger;
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }
}
