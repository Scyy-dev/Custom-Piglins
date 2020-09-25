package me.Scyy.CustomPiglins.Config;

import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DefaultConfig extends ConfigFile {

    private final ItemStack piglinConverter;

    public DefaultConfig(Plugin plugin) {
        super(plugin, "config.yml");

        // Get all the data from config
        Material pcMaterial = Material.getMaterial(config.getString("piglinConverter.material", "BARRIER"));
        String rawName = config.getString("piglinConverter.name", "NAME_NOT_FOUND");
        List<String> rawLore = config.getStringList("piglinConverter.lore");
        boolean isShiny = config.getBoolean("piglinConverter.isShiny", false);

        // Create the item builder
        ItemBuilder pcBuilder = new ItemBuilder(pcMaterial);

        // Provide the name
        pcBuilder.name(ChatColor.translateAlternateColorCodes('&', rawName));

        // Provide the lore
        for (String lore : rawLore) {

            pcBuilder.lore(ChatColor.translateAlternateColorCodes('&', lore));

        }

        // Provide the enchantments
        if (isShiny) pcBuilder.enchant();

        // Create the item
        piglinConverter = pcBuilder.build();
    }

    public ItemStack getPiglinConverter() {
        return piglinConverter;
    }
}
