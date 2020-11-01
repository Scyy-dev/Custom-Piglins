package me.Scyy.CustomPiglins.Config;

import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DefaultConfig extends ConfigFile {

    private final ItemStack consumableConverter;

    private final ItemStack nonConsumableConverter;

    public DefaultConfig(Plugin plugin) {
        super(plugin, "config.yml");

        // Get the Materials from config
        String rawCPCMaterial = config.getString("piglinConverter.consumable.material");
        String rawNCPCMaterial = config.getString("piglinConverter.non-consumable.material");
        Material cPCMaterial;
        Material ncPCMaterial;

        // Validate the material
        if (rawCPCMaterial == null || rawNCPCMaterial == null) {

            plugin.getLogger().warning("Could not find materials for piglins converters in config!");
            cPCMaterial = Material.ACACIA_BOAT;
            ncPCMaterial = Material.ACACIA_BOAT;

        } else {

            cPCMaterial = Material.getMaterial(rawCPCMaterial);
            ncPCMaterial = Material.getMaterial(rawNCPCMaterial);

        }

        // Get the raw names from config
        String cPCRawName = config.getString("piglinConverter.consumable.name", "NAME_NOT_FOUND");
        String ncPCRawName = config.getString("piglinConverter.non-consumable.name", "NAME_NOT_FOUND");
        if (cPCRawName == null || ncPCRawName == null) {

            plugin.getLogger().warning("Could not find materials for piglins converters in config!");
            cPCRawName = "NAME_NOT_FOUND";
            ncPCRawName = "NAME_NOT_FOUND";

        }

        // Get the lore from config
        List<String> cPCRawLore = config.getStringList("piglinConverter.consumable.lore");
        List<String> ncPCRawLore = config.getStringList("piglinConverter.non-consumable.lore");

        // get the shiny factor from config
        boolean cPCIsShiny = config.getBoolean("piglinConverter.consumable.isShiny", false);
        boolean ncPCIsShiny = config.getBoolean("piglinConverter.non-consumable.isShiny", false);

        // Create the item builders
        ItemBuilder cPCBuilder = new ItemBuilder(cPCMaterial);
        ItemBuilder ncPCBuilder = new ItemBuilder(ncPCMaterial);

        // Provide the names
        cPCBuilder.name(ChatColor.translateAlternateColorCodes('&', cPCRawName));
        ncPCBuilder.name(ChatColor.translateAlternateColorCodes('&', ncPCRawName));

        // Provide the lore
        for (String lore : cPCRawLore) {

            cPCBuilder.lore(ChatColor.translateAlternateColorCodes('&', lore));

        }
        for (String lore : ncPCRawLore) {

            ncPCBuilder.lore(ChatColor.translateAlternateColorCodes('&', lore));

        }

        // Provide the shiny factor
        if (cPCIsShiny) cPCBuilder.enchant();
        if (ncPCIsShiny) ncPCBuilder.enchant();

        // Create the items
        consumableConverter = cPCBuilder.build();
        nonConsumableConverter = ncPCBuilder.build();

    }

    public ItemStack getConsumableConverter() {
        return consumableConverter;
    }

    public ItemStack getNonConsumableConverter() {
        return nonConsumableConverter;
    }
}
