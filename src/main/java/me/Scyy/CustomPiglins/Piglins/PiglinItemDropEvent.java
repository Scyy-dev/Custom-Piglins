package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PiglinItemDropEvent implements Listener {

    private final PiglinLootGenerator generator;

    private final Plugin plugin;

    public PiglinItemDropEvent(PiglinLootGenerator generator, Plugin plugin) {

        this.generator = generator;
        this.plugin = plugin;

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

        // Drop the item in the world
        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), replacement);

    }
}
