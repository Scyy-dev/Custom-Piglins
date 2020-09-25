package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Config.DefaultConfig;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ZombifiedPiglinInteractEvent implements Listener {

    private final DefaultConfig config;

    private final ItemStack piglinConverter;

    public ZombifiedPiglinInteractEvent(Plugin plugin) {

        config = plugin.getConfigFileHandler().getDefaultConfig();
        piglinConverter = config.getPiglinConverter();
        if (piglinConverter.getItemMeta() == null || piglinConverter.getItemMeta().getLore() == null) {

            throw new IllegalArgumentException("piglinConverter must have lore");

        }

    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEntityEvent event) {

        if (event.getRightClicked().getType() != EntityType.ZOMBIFIED_PIGLIN) return;

        PigZombie pigZombie = (PigZombie) event.getRightClicked();

        ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();

        if (mainHand.getType() != piglinConverter.getType()) return;

        if (mainHand.getItemMeta() == null) return;

        if (mainHand.getItemMeta().getLore() == null) return;

        if (!mainHand.getItemMeta().getLore().equals(piglinConverter.getItemMeta().getLore())) return;

        // Remove the zombified piglin
        event.getRightClicked().remove();

        // Spawn a new piglin
        Piglin piglin = event.getRightClicked().getWorld().spawn(event.getRightClicked().getLocation(), Piglin.class);

        // Set the piglin to not zombify
        piglin.setImmuneToZombification(true);

        // Check if it is an adult or baby
        if (pigZombie.isAdult()) piglin.setAdult();
        else piglin.setBaby();

        // Get the name of the new piglin from config
        String piglinName = config.getConfig().getString("piglinConverter.convertedName");

        // give it a custom name so it doesn't despawn
        piglin.setCustomName(ChatColor.translateAlternateColorCodes('&', piglinName));

        // Cancel the event
        event.setCancelled(true);

        // Check if the item needs to be removed
        if (config.getConfig().getBoolean("piglinConverter.isConsumable")) {



        }


    }
}
