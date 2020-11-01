package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

// # # # # # # # # #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # 0 0 0 0 0 0 0 #
// # X # # # # # C #
// X = Cancel adding items
// C = Confirm adding items

public class PiglinItemAdderGUI extends InventoryGUI {

    public PiglinItemAdderGUI(GUIContext context, Plugin plugin) {
        super(context, plugin);

        // Create the empty space
        for (int inventoryIndex = 10; inventoryIndex < 43; inventoryIndex++) {

            inventoryItems[inventoryIndex] = null;

            // Check if the inventory slot is one from the edge, and move index to other side
            if ((inventoryIndex - 7) % 9 == 0) inventoryIndex += 2;

        }

        // Create the Cancel button
        inventoryItems[46] = new ItemBuilder(Material.BARRIER).name("&6Cancel").lore(
                "&7Click me to cancel adding items").build();

        // Create the Confirm Button
        inventoryItems[52] = new ItemBuilder(Material.EMERALD_BLOCK).name("&6Confirm").lore(
                "&7Click me to confirm adding items").build();

    }

    @Override
    public void update(InventoryClickEvent event) {

        // No context changes are needed, so handle the click
        this.handleClick(event);

    }

    @Override
    public InventoryGUI handleClick(InventoryClickEvent event) {

         int clickedSlot = event.getRawSlot();

         boolean clickedAddingSlotSpace = false;

        // Check if the slot clicked was within the adding item area
        if (9 < clickedSlot && clickedSlot < 17) clickedAddingSlotSpace = true;
        else if (18 < clickedSlot && clickedSlot < 26) clickedAddingSlotSpace = true;
        else if (27 < clickedSlot && clickedSlot < 35) clickedAddingSlotSpace = true;
        else if (36 < clickedSlot && clickedSlot < 44) clickedAddingSlotSpace = true;

         if (clickedAddingSlotSpace) return this;
         else {

             if (clickedSlot == 46 && inventoryItems[46].getType() == Material.BARRIER) {

                 event.setCancelled(true);
                 return new PiglinItemListGUI(context, plugin);

             } else if (clickedSlot == 52 && inventoryItems[52].getType() == Material.EMERALD_BLOCK) {

                 event.setCancelled(true);
                 this.addItems(event.getView().getTopInventory().getContents());
                 return new PiglinItemListGUI(context, plugin);

             } else {

                 event.setCancelled(true);
                 return this;

             }

         }

    }

    private void addItems(ItemStack[] inventory) {

        for (int inventoryIndex = 10; inventoryIndex < 43; inventoryIndex++) {

            ItemStack item = inventory[inventoryIndex];

            if (item != null && item.getType() != Material.AIR) {

                plugin.getGenerator().addPiglinItem(item, 1, 1, 1, false,false);

            }

            // Check if the inventory slot is one from the edge, and move index to other side
            if ((inventoryIndex - 8) % 9 == 0) inventoryIndex += 2;

        }

    }
}
