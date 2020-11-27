package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Config.DefaultConfig;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractEvent(PlayerInteractEntityEvent event) {

        if (event.isCancelled()) return;

        // Validate the entity clicked is a zombified piglin
        if (event.getRightClicked().getType() != EntityType.ZOMBIFIED_PIGLIN) return;

        // Get data about the event
        PigZombie pigZombie = (PigZombie) event.getRightClicked();
        ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();

        // Validate the item used
        if (!(mainHand.isSimilar(consumableConverter) || mainHand.isSimilar(nonComsumableConverter))) return;

        // Validate the data about the item used
        if (mainHand.getItemMeta() == null) return;
        if (mainHand.getItemMeta().getLore() == null) return;

        // Determine if the converter is consumable or not
        boolean isConsumable = false;
        if (mainHand.getType() == consumableConverter.getType()) isConsumable = true;

        // EntityEquipment pigZombieInv = pigZombie.getEquipment();

        // Remove the zombified piglin
        event.getRightClicked().remove();

        /*
        AbstractPiglin piglin;
        if (pigZombieInv != null && pigZombieInv.getItemInMainHand().getType() == Material.GOLDEN_AXE) {
            piglin = event.getRightClicked().getWorld().spawn(event.getRightClicked().getLocation(), PiglinBrute.class);
        } else {
            piglin = event.getRightClicked().getWorld().spawn(event.getRightClicked().getLocation(), Piglin.class);
        }
         */


        // Spawn a new piglin
        Piglin piglin = event.getRightClicked().getWorld().spawn(event.getRightClicked().getLocation(), Piglin.class);

        // Set the piglin to not zombify
        piglin.setImmuneToZombification(true);

        // Check if it is an adult or baby
        if (pigZombie.isAdult()) piglin.setAdult();
        else piglin.setBaby();

        // Set the custom name
        String piglinName = event.getRightClicked().getCustomName();
        if (piglinName != null && isConsumable) {
            piglinName = config.getConfig().getString("piglinConverter.consumable.convertedName");
        } else if (piglinName != null) {
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

            event.getPlayer().getInventory().setItemInMainHand(null);
            if (mainHand.getAmount() > 1) {
                ItemStack newHand = mainHand.clone();
                newHand.setAmount(mainHand.getAmount() - 1);
                Bukkit.getScheduler().runTask(plugin, () -> event.getPlayer().getInventory().setItemInMainHand(newHand));
            }

        }

    }

}
