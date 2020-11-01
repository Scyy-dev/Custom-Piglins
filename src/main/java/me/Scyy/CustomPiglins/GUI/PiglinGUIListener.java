package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;

public class PiglinGUIListener implements Listener {

    private final Plugin plugin;

    public PiglinGUIListener(Plugin plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        // Check if the click was a null click
        if (event.getView().getTopInventory().getHolder() == null) return;
      
        // Check if the inventory clicked is an inventory defined by this plugin
        if (!(event.getView().getTopInventory().getHolder() instanceof InventoryGUI)) return;

        InventoryGUI oldGUI = (InventoryGUI) event.getView().getTopInventory().getHolder();

        if (!oldGUI.allowPlayerInventoryEdits() && event.getClickedInventory() instanceof PlayerInventory) {
            event.setCancelled(true);
            return;
        }

        InventoryGUI updatedGUI = oldGUI.update(event);

        if (oldGUI.shouldReopen()) {

            // Reopen the new inventory
            Bukkit.getScheduler().runTask(plugin, () -> event.getWhoClicked().openInventory(updatedGUI.getInventory()));

        } else {

            // Update the inventory contents
            event.getView().getTopInventory().setContents(updatedGUI.getInventoryItems());

            // Update the players inventory
            Bukkit.getScheduler().runTask(plugin, () -> ((Player) event.getWhoClicked()).updateInventory());

        }

    }

}
