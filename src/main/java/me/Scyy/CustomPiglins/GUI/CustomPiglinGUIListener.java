package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CustomPiglinGUIListener implements Listener {

    private final Plugin plugin;

    public CustomPiglinGUIListener(Plugin plugin) {

        this.plugin = plugin;

    }

    public void onInventoryClickEvent(InventoryClickEvent event) {

        if (!(event.getView().getTitle().equalsIgnoreCase(CustomPiglinGUI.INVENTORY_NAME))) return;

        System.out.println("nice");



    }
}
