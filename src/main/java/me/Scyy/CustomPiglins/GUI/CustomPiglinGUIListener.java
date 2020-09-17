package me.Scyy.CustomPiglins.GUI;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CustomPiglinGUIListener implements Listener {

    public void onInventoryClickEvent(InventoryClickEvent event) {

        if (!(event.getView().getTitle().equalsIgnoreCase(CustomPiglinGUI.INVENTORY_NAME))) return;

        System.out.println("nice");



    }
}
