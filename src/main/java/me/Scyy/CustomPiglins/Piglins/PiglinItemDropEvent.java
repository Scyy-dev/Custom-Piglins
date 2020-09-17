package me.Scyy.CustomPiglins.Piglins;

import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PiglinItemDropEvent implements Listener {

    private final CustomPiglinLootGenerator generator;

    private final Plugin plugin;

    public PiglinItemDropEvent(CustomPiglinLootGenerator generator, Plugin plugin) {

        this.generator = generator;
        this.plugin = plugin;

    }

    public void onItemDropEvent(EntityDropItemEvent event) {

        // Ignore the event if it isn't from a Piglin
        if (event.getEntityType() != EntityType.PIGLIN) return;

        // Cancel the item being dropped if it is from a Piglin
        event.getItemDrop().remove();

        // Generate a replacement item
        ItemStack replacement = generator.generateItem();

        // Drop the item in the world
        event.getEntity().getWorld().dropItemNaturally(event.getItemDrop().getLocation(), replacement);

    }
}
