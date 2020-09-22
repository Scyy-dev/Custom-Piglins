package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CustomPiglinGUIListener implements Listener {

    private final Plugin plugin;

    private final CustomPiglinLootGenerator generator;

    public CustomPiglinGUIListener(Plugin plugin) {

        this.plugin = plugin;
        this.generator = plugin.getGenerator();

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        // Check if the click was a null click
        if (event.getClickedInventory() == null) return;

        // Check if the inventory clicked is an inventory defined by this plugin
        if (!(event.getClickedInventory().getHolder() instanceof InventoryGUI)) return;

        // Cancel the event
        event.setCancelled(true);





    }
}
