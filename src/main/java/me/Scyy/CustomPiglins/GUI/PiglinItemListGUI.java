package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.PiglinLootGenerator;
import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

// # # # # # # # # #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # P # # # # # N #
// # # # # E # # # #
// P = previous page
// N = next page
// E = Piglin Item Adder

public class PiglinItemListGUI extends InventoryGUI {

    public PiglinItemListGUI(GUIContext context, Plugin plugin) {
        super(context, plugin);

        // Get the generator from the UI
        PiglinLootGenerator generator = plugin.getGenerator();

        // Get the simplified version of the PiglinItemIDs
        ArrayList<PiglinItem> piglinItems = new ArrayList<>(generator.getPiglinItems());

        // Calculate the starting index for the items from the piglinItems array
        int piglinItemStartIndex = 21 * context.getPage();

        // Calculate the ending index
        int piglinItemEndIndex = 21 * (context.getPage() + 1);

        // inventory index tracks the inventory slot of each item, is irrelevant to the index for in piglinItems
        int inventoryIndex = 10;

        // Iterate over the defined range and add each item to the inventory
        for (int itemIndex = piglinItemStartIndex; itemIndex < piglinItemEndIndex; itemIndex++) {

            // Put the item in the array if it is accessible, otherwise add air
            if (itemIndex < piglinItems.size()) {

                // Get the Piglin Item
                PiglinItem piglinItem = piglinItems.get(itemIndex);

                // Add it the inventory array
                inventoryItems[inventoryIndex] = piglinItem.getItem();

            } else {

                // Add an empty value to the inventory array
                inventoryItems[inventoryIndex] = null;

            }

            // Increment the inventory index
            inventoryIndex++;

            // Check if the inventory slot is one from the edge, and move index to other side
            if ((inventoryIndex - 8) % 9 == 0) inventoryIndex += 2;

        }

        // Check if the page is not 0 and if so add the previous pagination arrow
        if (context.getPage() != 0) {

            inventoryItems[37] = new ItemBuilder(Material.ARROW).name("&6Page " + context.getPage()).build();

        }

        // determine the page number
        int nextPageNum = context.getPage() + 2;

        // Add the next pagination arrow
        inventoryItems[43] = new ItemBuilder(Material.ARROW).name("&6Page " + nextPageNum).build();

        // Add the piglin item adder button
        inventoryItems[49] = new ItemBuilder(Material.EMERALD_BLOCK).name("&6Add Items").build();

    }

    @Override
    public void update(InventoryClickEvent event) {

        // Get the contents of the inventory
        ItemStack[] contents = event.getView().getTopInventory().getContents();

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

        // Update the context
        this.setContext(context);

        // Handle the click in the old GUI and hence create the new GUI
        InventoryGUI newGUI = this.handleClick(event);

        // Update the inventory contents
        event.getView().getTopInventory().setContents(newGUI.getInventoryItems());

        // Update the players inventory
        Bukkit.getScheduler().runTask(plugin, () -> ((Player) event.getWhoClicked()).updateInventory());

    }

    @Override
    public InventoryGUI handleClick(InventoryClickEvent event) {

        int clickedSlot = event.getRawSlot();

        // slot of the item in the piglin item list
        int piglinItemSlot = -1;

        // Check if the item clicked was a piglin item in the inventory
        if (9 < clickedSlot && clickedSlot < 17) piglinItemSlot = clickedSlot - 10;
        else if (18 < clickedSlot && clickedSlot < 26) piglinItemSlot = clickedSlot - 12;
        else if (27 < clickedSlot && clickedSlot < 35) piglinItemSlot = clickedSlot - 14;

        // Check if the item clicked was a piglinItem
        if (piglinItemSlot != -1 && inventoryItems[clickedSlot] != null) {

            // Calculate the index in the array from the generator
            int piglinArraySlot = piglinItemSlot + 21 * context.getPage();

            // Get the item from the generator
            PiglinItem piglinItem = new ArrayList<>(plugin.getGenerator().getPiglinItems()).get(piglinArraySlot);

            // Create the GUI context
            GUIContext context = new GUIContext(piglinItem, this.context.getPlayer(), this.context.getPage());

            // Cancel the event
            event.setCancelled(true);

            // Return a new PiglinItem page
            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was a back page arrow
        if (clickedSlot == 37 && inventoryItems[clickedSlot].getType() == Material.ARROW) {

            // decrement the page
            context.setPage(context.getPage() - 1);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemListGUI(context, plugin);

        }

        // Check the item clicked was a forward page arrow
        if (clickedSlot == 43 && inventoryItems[clickedSlot].getType() == Material.ARROW) {

            // increment the page
            context.setPage(context.getPage() + 1);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemListGUI(context, plugin);

        }

        // Check if the item clicked was the Piglin Item Adder
        if (clickedSlot == 49 && inventoryItems[clickedSlot].getType() == Material.EMERALD_BLOCK) {

            // Cancel the event
            event.setCancelled(true);

            // Open the Piglin Item Adder
            return new PiglinItemAdderGUI(context, plugin);

        }


        // Cancel the event
        event.setCancelled(true);

        // If no item had an affect, change nothing
        return this;
    }
}
