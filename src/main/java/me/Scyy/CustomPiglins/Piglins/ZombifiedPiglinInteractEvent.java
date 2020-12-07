package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Config.DefaultConfig;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class ZombifiedPiglinInteractEvent implements Listener {

    private final Plugin plugin;

    private final DefaultConfig config;

    private final ItemStack consumableConverter;

    private final ItemStack nonConsumableConverter;

    public ZombifiedPiglinInteractEvent(Plugin plugin) {

        // Get the data from the plugin
        this.plugin = plugin;
        config = plugin.getConfigFileHandler().getDefaultConfig();
        consumableConverter = plugin.getConfigFileHandler().getDefaultConfig().getConsumableConverter();
        nonConsumableConverter = plugin.getConfigFileHandler().getDefaultConfig().getNonConsumableConverter();

        // Check if the items loaded are valid
        if (consumableConverter.getItemMeta() == null
                || consumableConverter.getItemMeta().getLore() == null) {

            throw new IllegalArgumentException("Consumable Piglin Converter must have lore");

        }
        if (nonConsumableConverter.getItemMeta() == null
                || nonConsumableConverter.getItemMeta().getLore() == null) {

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
        if (!(mainHand.isSimilar(consumableConverter) || mainHand.isSimilar(nonConsumableConverter))) return;

        // Validate the data about the item used
        if (mainHand.getItemMeta() == null) return;
        if (mainHand.getItemMeta().getLore() == null) return;

        // Determine if the converter is consumable or not
        boolean isConsumable = false;
        if (mainHand.isSimilar(consumableConverter)) isConsumable = true;

        EntityEquipment pigZombieInv = pigZombie.getEquipment();

        // Remove the zombified piglin
        event.getRightClicked().remove();


        PiglinAbstract piglin;
        if (pigZombieInv != null && pigZombieInv.getItemInMainHand().getType() == Material.GOLDEN_AXE) {
            piglin = event.getRightClicked().getWorld().spawn(event.getRightClicked().getLocation(), PiglinBrute.class);
        } else {
            piglin = event.getRightClicked().getWorld().spawn(event.getRightClicked().getLocation(), Piglin.class);
        }

        // Set the piglin to not zombify
        piglin.setImmuneToZombification(true);

        // Check if it is an adult or baby
        if (!pigZombie.isAdult() && !(piglin instanceof PiglinBrute)) piglin.setBaby();
        else if (!(piglin instanceof PiglinBrute)) piglin.setAdult();

        // Set the custom name
        if (pigZombie.getCustomName() != null) {
            piglin.setCustomName(pigZombie.getCustomName());
        }


        if (!isConsumable && event.getPlayer().hasPermission("custompiglins.converter.nonconsumable")) {

            // Remove the item and add it back a tick later to prevent more than one mob spawning
            event.getPlayer().getInventory().setItemInMainHand(null);
            Bukkit.getScheduler().runTask(plugin, () -> event.getPlayer().getInventory().setItemInMainHand(mainHand));

        } else if (isConsumable && event.getPlayer().hasPermission("custompiglins.converter.consumable")) {

            event.getPlayer().getInventory().setItemInMainHand(null);
            if (mainHand.getAmount() > 1) {
                ItemStack newHand = mainHand.clone();
                newHand.setAmount(mainHand.getAmount() - 1);
                Bukkit.getScheduler().runTask(plugin, () -> event.getPlayer().getInventory().setItemInMainHand(newHand));
            }

        }

    }

}
