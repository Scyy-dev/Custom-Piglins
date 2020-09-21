package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CustomPiglinGUIListener implements Listener {

    private final Plugin plugin;

    private final CustomPiglinLootGenerator generator;

    public CustomPiglinGUIListener(Plugin plugin) {

        this.plugin = plugin;
        this.generator = plugin.getGenerator();

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        if (!(event.getView().getTitle().equalsIgnoreCase(InventoryGUI.INVENTORY_NAME))) return;

        event.setCancelled(true);





    }
}
