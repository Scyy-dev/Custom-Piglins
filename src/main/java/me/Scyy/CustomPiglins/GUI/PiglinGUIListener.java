package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.PiglinLootGenerator;
import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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

        // Check if the inventory clicked is an inventory defined by this plugin
        if (!(event.getClickedInventory().getHolder() instanceof InventoryGUI)) return;

        // Get the contents of the inventory
        ItemStack[] contents = event.getView().getTopInventory().getContents();

        // Handle clicks in the Piglin Item List inventory
        if (contents[43] != null && contents[43].getType() == Material.ARROW) {

            // Check if the item has meta
            if (contents[43].getItemMeta() == null) {

                // Log an error
                plugin.getLogger().warning("Error getting the piglin Item!");

                return;

            }

            // Get the page reference from the GUI
            int nextPage = Integer.parseInt(contents[43].getItemMeta().getDisplayName().split(" ")[1]);

            // Create the context of the old GUI
            GUIContext context = new GUIContext(null, (Player) event.getWhoClicked(), nextPage - 2);

            // Create an instance of the old GUI
            PiglinItemListGUI gui = new PiglinItemListGUI(context, plugin);

            // Handle the click in the old GUI and hence create the new GUI
            InventoryGUI newGUI = gui.handleClick(event);

            // Update the inventory contents
            event.getClickedInventory().setContents(newGUI.getInventoryItems());

            // Update the players inventory
            Bukkit.getScheduler().runTask(plugin, () -> ((Player) event.getWhoClicked()).updateInventory());

        // Handle clicks in the Piglin Item Inventory
        } else if (contents[53] != null && contents[53].getType() == Material.NETHER_STAR) {

            // Check if the item has meta
            if (contents[0].getItemMeta() == null) {

                // Log an error
                plugin.getLogger().warning("Error getting the piglin Item!");

                return;

            }

            // Get the ID of the piglin item from the UI
            int piglinItemID = Integer.parseInt(contents[0].getItemMeta().getDisplayName());

            // Get the piglin item from the generator
            PiglinItem item = generator.getPiglinItem(piglinItemID);

            // Create the context of the old GUI
            GUIContext context = new GUIContext(item, (Player) event.getWhoClicked(), 0);

            // Create an instance of the old GUI
            PiglinItemGUI gui = new PiglinItemGUI(context, plugin);

            // Handle the click in the old GUI and hence create the new GUI
            InventoryGUI newGUI = gui.handleClick(event);

            // Update the inventory contents
            event.getClickedInventory().setContents(newGUI.getInventoryItems());

            // Update the inventory
            Bukkit.getScheduler().runTask(plugin, () -> ((Player) event.getWhoClicked()).updateInventory());
            
        }

    }

}
