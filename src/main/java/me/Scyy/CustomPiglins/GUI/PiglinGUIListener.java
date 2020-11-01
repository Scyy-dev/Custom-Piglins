package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.PiglinLootGenerator;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class PiglinGUIListener implements Listener {

    private final Plugin plugin;

    private final PiglinLootGenerator generator;

    public PiglinGUIListener(Plugin plugin) {

        this.plugin = plugin;
        this.generator = plugin.getGenerator();

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        // Check if the click was a null click
        if (event.getClickedInventory() == null) return;

        InventoryHolder invHolder = event.getView().getTopInventory().getHolder();

        // Check if the inventory clicked is an inventory defined by this plugin
        if (!(invHolder instanceof InventoryGUI)) return;

        InventoryGUI inventoryGUI = (InventoryGUI) invHolder;

        // Update the inventory based upon the event
        inventoryGUI.update(event);

    }

}
