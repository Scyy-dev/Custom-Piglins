package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Config.DefaultConfig;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ZombifiedPiglinInteractEvent implements Listener {

    private final Plugin plugin;

    private final DefaultConfig config;

    private final ItemStack consumableConverter;

    private final ItemStack nonComsumableConverter;

    public ZombifiedPiglinInteractEvent(Plugin plugin) {

        // Get the data from the plugin
        this.plugin = plugin;
        config = plugin.getConfigFileHandler().getDefaultConfig();
        consumableConverter = plugin.getConfigFileHandler().getDefaultConfig().getConsumableConverter();
        nonComsumableConverter = plugin.getConfigFileHandler().getDefaultConfig().getNonConsumableConverter();

        // Check if the items loaded are valid
        if (consumableConverter.getItemMeta() == null
                || consumableConverter.getItemMeta().getLore() == null) {

            throw new IllegalArgumentException("Consumable Piglin Converter must have lore");

        }
        if (nonComsumableConverter.getItemMeta() == null
                || nonComsumableConverter.getItemMeta().getLore() == null) {

            throw new IllegalArgumentException("Consumable Piglin Converter must have lore");

        }

    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEntityEvent event) {

        // Validate the entity clicked is a zombified piglin
        if (event.getRightClicked().getType() != EntityType.ZOMBIFIED_PIGLIN) return;

        // Get data about the event
        PigZombie pigZombie = (PigZombie) event.getRightClicked();
        ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();

        // Validate the item used
        if (!(mainHand.equals(consumableConverter) || mainHand.equals(nonComsumableConverter))) return;

        // Validate the data about the item used
        if (mainHand.getItemMeta() == null) return;
        if (mainHand.getItemMeta().getLore() == null) return;

        // Determine if the converter is consumable or not
        boolean isConsumable = false;
        if (mainHand.getType() == consumableConverter.getType()) isConsumable = true;

        // Remove the zombified piglin
        event.getRightClicked().remove();

        // Spawn a new piglin
        Piglin piglin = event.getRightClicked().getWorld().spawn(event.getRightClicked().getLocation(), Piglin.class);

        // Set the piglin to not zombify
        piglin.setImmuneToZombification(true);

        // Check if it is an adult or baby
        if (pigZombie.isAdult()) piglin.setAdult();
        else piglin.setBaby();

        // Set the custom name
        String piglinName;
        if (isConsumable) {
            piglinName = config.getConfig().getString("piglinConverter.consumable.convertedName");
        } else {
            piglinName = config.getConfig().getString("piglinConverter.non-consumable.convertedName");
        }
        piglin.setCustomName(ChatColor.translateAlternateColorCodes('&', piglinName));

        // Cancel the event
        event.setCancelled(true);

        if (!isConsumable && event.getPlayer().hasPermission("custompiglins.converter.nonconsumable")) {

            // Remove the item and add it back a tick later to prevent more than one mob spawning
            event.getPlayer().getInventory().setItemInMainHand(null);
            Bukkit.getScheduler().runTask(plugin, () -> event.getPlayer().getInventory().setItemInMainHand(mainHand));

        } else if (isConsumable) {

            if (mainHand.getAmount() != 1) {

                mainHand.setAmount(mainHand.getAmount() - 1);

            } else {

                event.getPlayer().getInventory().setItemInMainHand(null);

            }

        }

    }

}
