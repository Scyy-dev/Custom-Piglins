package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

// # # # # # # # # #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # # # # # # # # #
// # # # # # # # # #

public class PiglinItemGUI extends InventoryGUI {

    public PiglinItemGUI(int page, Player player, Plugin plugin) {
        super(page, player, plugin);

        // Get the generator from the UI
        CustomPiglinLootGenerator generator = plugin.getGenerator();

        // Get the simplified version of the PiglinItemIDs
        ArrayList<PiglinItem> piglinItems = new ArrayList<>(generator.getPiglinItems());

        // Calculate the starting index for the items from the piglinItems array
        int piglinItemStartIndex = 21 * page;

        // Calculate the ending index
        int piglinItemEndIndex = 27*(page + 1) + 1;

        // Check if the end of the piglin items has been reached
        if (piglinItemEndIndex > piglinItems.size()) piglinItemEndIndex = piglinItems.size();

        // Check if the page number is greater than the number of pages available
        if (piglinItemStartIndex > piglinItems.size()) piglinItemStartIndex = piglinItemEndIndex;

        // inventory index tracks the inventory slot of each item, is irrelevant to the index for in piglinItems
        int inventoryIndex = 10;

        // Iterate over the defined range and add each item to the inventory
        for (int itemIndex = piglinItemStartIndex; itemIndex < piglinItemEndIndex; itemIndex++) {

            // Get the Piglin Item
            PiglinItem piglinItem = piglinItems.get(itemIndex);

            // Put the item in the array
            inventoryItems[inventoryIndex] = piglinItem.getItem();

            // Increment the inventory index
            inventoryIndex++;

            // Check if the inventory slot is at the edge, and move it over
            if ((inventoryIndex - 8) % 9 == 0) inventoryIndex += 2;

        }

        // Assign the inventory items to the inventory
        inventory.setContents(inventoryItems);

    }
}
