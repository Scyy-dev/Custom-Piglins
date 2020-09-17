package me.Scyy.CustomPiglins;

import me.Scyy.CustomPiglins.Config.ConfigFileHandler;
import me.Scyy.CustomPiglins.GUI.CustomPiglinGUIListener;
import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import me.Scyy.CustomPiglins.Piglins.PiglinItemDropEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private ConfigFileHandler configFileHandler;

    private CustomPiglinLootGenerator generator;

    @Override
    public void onEnable() {

        // Load configs
        this.configFileHandler = new ConfigFileHandler(this);

        // Create the loot generator
        generator = new CustomPiglinLootGenerator(this);

        // Register the command
        CustomPiglinCommand customPiglinCommand = new CustomPiglinCommand(generator, this);
        this.getCommand("custompiglins").setExecutor(customPiglinCommand);
        this.getCommand("custompiglins").setTabCompleter(customPiglinCommand);

        // Register the item drop listener
        Bukkit.getPluginManager().registerEvents(new PiglinItemDropEvent(generator, this), this);

        // Register the inventory listener
        Bukkit.getPluginManager().registerEvents(new CustomPiglinGUIListener(this), this);

    }

    public void reloadConfigs() {

        configFileHandler.reloadConfigs();

    }

    public CustomPiglinLootGenerator getGenerator() {
        return generator;
    }

    public ConfigFileHandler getConfigFileHandler() {
        return configFileHandler;
    }
}


