package me.Scyy.CustomPiglins.Piglins;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PiglinItemDropEvent implements Listener {

    private final PiglinLootGenerator generator;

    public PiglinItemDropEvent(PiglinLootGenerator generator) {

        this.generator = generator;

    }

    @EventHandler
    public void onItemDropEvent(EntityDropItemEvent event) {

        // Ignore the event if it isn't from a Piglin
        if (event.getEntityType() != EntityType.PIGLIN) return;

        // Cancel the item being dropped if it is from a Piglin
        event.getItemDrop().remove();

        // Generate a replacement item
        ItemStack replacement = generator.generateItem();

        // Check if the item generated was air
        if (replacement.getType() == Material.AIR) return;

        // Find the exact centre of the block this item is dropped at
        Location rawLocation = event.getEntity().getLocation();

        // Calculate the new location of the drop
        float newLocX = rawLocation.getBlockX() + 0.5f;
        float newLocY = rawLocation.getBlockY() + 0.5f;
        float newLocZ = rawLocation.getBlockZ() + 0.5f;

        // Create the new location
        Location newLocation = new Location(rawLocation.getWorld(), newLocX, newLocY, newLocZ);

        // Drop the item in the world
        event.getEntity().getWorld().dropItem(newLocation, replacement);

    }
}
