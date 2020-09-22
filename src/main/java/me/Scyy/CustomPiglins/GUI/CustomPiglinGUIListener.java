package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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

        // Get the contents of the inventory
        ItemStack[] contents = event.getInventory().getContents();

        // Handle clicks in the Piglin Item List inventory
        if (contents[43] != null && contents[43].getType() == Material.ARROW) {

            // Get the page reference from the GUI
            int nextPage = Integer.parseInt(contents[43].getItemMeta().getDisplayName().split(" ")[1]);

            // Get the GUIContext for the current inventory
            GUIContext context = new GUIContext(null, (Player) event.getWhoClicked(), nextPage - 2);

            // Create the inventory
            PiglinItemListGUI gui = new PiglinItemListGUI(context, plugin);

            // Update the inventory
            InventoryGUI newGUI = gui.handleClick(event.getRawSlot(), event.getClick());

            // Open the nwe inventory
            event.getWhoClicked().openInventory(newGUI.getInventory());

        // Handle clicks in the Piglin Item Inventory
        } else if (contents[53] != null && contents[53].getType() == Material.NETHER_STAR) {



        }
    }
}
