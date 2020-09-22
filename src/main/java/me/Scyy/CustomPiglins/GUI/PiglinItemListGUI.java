package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;

// # # # # # # # # #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # P # # # # # N #
// # # # # S # # # #
// P = previous page
// N = next page
// S = settings

public class PiglinItemListGUI extends InventoryGUI {

    public PiglinItemListGUI(GUIContext context, Plugin plugin) {
        super(context, plugin);

        // Get the generator from the UI
        CustomPiglinLootGenerator generator = plugin.getGenerator();

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
            new ItemBuilder(inventoryItems[5]);

        }

        // determine the page number
        int nextPageNum = context.getPage() + 2;

        // Add the next pagination arrow
        inventoryItems[43] = new ItemBuilder(Material.ARROW).name("&6Page " + nextPageNum).build();

    }


        // Assign the inventory items to the inventory
        inventory.setContents(inventoryItems);

    }
}
