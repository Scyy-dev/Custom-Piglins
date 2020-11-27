package me.Scyy.CustomPiglins;

import me.Scyy.CustomPiglins.Config.ConfigFileHandler;
import me.Scyy.CustomPiglins.GUI.PiglinGUIListener;
import me.Scyy.CustomPiglins.Piglins.PiglinLootGenerator;
import me.Scyy.CustomPiglins.Piglins.PiglinItemDropEvent;
import me.Scyy.CustomPiglins.Piglins.ZombifiedPiglinInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private ConfigFileHandler configFileHandler;

    private PiglinLootGenerator generator;

    @Override
    public void onEnable() {

        // Load configs
        this.configFileHandler = new ConfigFileHandler(this);

        // Create the loot generator
        generator = new PiglinLootGenerator(this);

        // Register the command
        CustomPiglinCommand customPiglinCommand = new CustomPiglinCommand(this);
        this.getCommand("custompiglins").setExecutor(customPiglinCommand);
        this.getCommand("custompiglins").setTabCompleter(customPiglinCommand);

        // Register the item drop listener
        Bukkit.getPluginManager().registerEvents(new PiglinItemDropEvent(generator), this);

        // Register the inventory listener
        Bukkit.getPluginManager().registerEvents(new PiglinGUIListener(this), this);

        // Register the Piglin Converter listener
        Bukkit.getPluginManager().registerEvents(new ZombifiedPiglinInteractEvent(this), this);

    }

    @Override
    public void onDisable() {

        // Save the generator data
        this.getLogger().info("Saving Custom Piglin item data...");
        configFileHandler.getPiglinItemDataConfig().saveGeneratorData();
        this.getLogger().info("Custom Piglin Item Data saved!");

    }

    /**
     * Saves all generator data to config and load
     */
    public void reload(CommandSender sender) {
        try {
            sender.sendMessage("Saving custom piglin item data...");
            configFileHandler.getPiglinItemDataConfig().saveGeneratorData();
            sender.sendMessage("Custom piglin item data saved!");
            sender.sendMessage("reloading configs...");
            configFileHandler.reloadConfigs();
            generator.setPiglinItems(configFileHandler.getPiglinItemDataConfig().loadPiglinItems());
            sender.sendMessage("Successfully reloaded!");
        } catch (Exception e) {
            sender.sendMessage("Error reloading! See console for error!");
            e.printStackTrace();
        }

    }

    public PiglinLootGenerator getGenerator() {
        return generator;
    }

    public ConfigFileHandler getConfigFileHandler() {
        return configFileHandler;
    }
}


