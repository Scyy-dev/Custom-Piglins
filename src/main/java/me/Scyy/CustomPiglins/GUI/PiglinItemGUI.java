package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.meta.Damageable;

// # # # # # # # # #
// # # # # P # # # #
// # # # # # # # # #
// # # W D # m M # #
// # # # # # # # # #
// B # # # R # # # C
// P = Piglin Item
// W = Weighting
// D = Has Random Durability
//

public class PiglinItemGUI extends InventoryGUI {

    public PiglinItemGUI(GUIContext context, Plugin plugin) {
        super(context, plugin);

        // Get the piglinItem
        PiglinItem piglinItem = context.getPiglinItem();

        // Check if there is a usable context
        if (context.getPiglinItem() == null) throw new IllegalArgumentException("Cannot use null PiglinItem");

        // Set the ID so the the listener can get the PiglinItem
        inventoryItems[0] = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("" + piglinItem.getItemID()).build();

        // Set the item to be displayed
        inventoryItems[13] = piglinItem.getItem();

        // Set the Weighting item
        inventoryItems[29] = new ItemBuilder(Material.ANVIL).name("&6Weight: " + piglinItem.getWeight())
                .lore("&r&7Right click to increase!")
                .lore("&r&7Left click to decrease!")
                .build();

        // Initialise the damage item builder
        ItemBuilder damageItemBuilder = new ItemBuilder(Material.NETHERITE_SHOVEL).name("&6Random Durability");

        // Check if the item can be damaged
        if (piglinItem.getItem() instanceof Damageable) {

            // Toggle the random damage
            if (piglinItem.hasRandomDamage()) damageItemBuilder.enchant().lore("&r&7This item will have a random durability!");
            else damageItemBuilder.lore("&r&7This item will not have a random durability!");

            // Add toggle text
            damageItemBuilder.lore("&r&7Click to toggle!");

        // Let the user know the item does not support having random damage
        } else damageItemBuilder.lore("&r&cThis item does not have durability!");

        // Set the damage item
        inventoryItems[30] = damageItemBuilder.build();

        // Set the min amount item
        inventoryItems[32] = new ItemBuilder(Material.GOLD_NUGGET).name("&6Minimum Amount: " + piglinItem.getMinAmount())
                .lore("&r&7Right click to increase!")
                .lore("&r&7Left click to decrease!")
                .build();

        // Set the max amount item
        inventoryItems[33] = new ItemBuilder(Material.GOLD_BLOCK).name("&6Maximum Amount: " + piglinItem.getMaxAmount())
                .lore("&r&7Right click to increase!")
                .lore("&r&7Left click to decrease!")
                .build();

        // Set the back arrow item
        inventoryItems[45] = new ItemBuilder(Material.ARROW).name("&6Back").build();

        // Set the remove item
        inventoryItems[49] = new ItemBuilder(Material.BARRIER).name("&cRemove Item").build();

        // Get the chance of the item being dropped
        double itemChance = plugin.getGenerator().getChance(piglinItem);

        // Round the chance
        double roundedChance = (double) Math.round(itemChance * 10000) / 10000;

        // Add the item
        inventoryItems[53] = new ItemBuilder(Material.NETHER_STAR).name("&6Drop Chance: " + roundedChance).build();

    }

    @Override
    public InventoryGUI handleClick(int clickedSlot, ClickType clickType) {

        // Check if the item clicked was the weight counter
        if (clickedSlot == 29 && inventoryItems[29] != null) {

            int weight = context.getPiglinItem().getWeight();

            if (clickType.isRightClick()) context.getPiglinItem().setWeight(weight + 1);
            else if (clickType.isLeftClick() && weight > 1) context.getPiglinItem().setWeight(weight - 1);

            // TODO - update the generator with the piglin item

            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was the random damage toggle
        if (clickedSlot == 30 && inventoryItems[30] != null) {

            boolean hasRandomDamage = context.getPiglinItem().hasRandomDamage();

            context.getPiglinItem().setRandomDamage(!hasRandomDamage);

            // TODO - update the generator with the piglin item

            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was the minimum amount counter
        if (clickedSlot == 32 && inventoryItems[32] != null) {

            int minAmount = context.getPiglinItem().getMinAmount();

            if (clickType.isLeftClick()) context.getPiglinItem().setMinAmount(minAmount + 1);
            else if (clickType.isRightClick() && minAmount > 1) context.getPiglinItem().setMinAmount(minAmount - 1);

            // TODO - update the generator with the piglin item

            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was the minimum amount counter
        if (clickedSlot == 33 && inventoryItems[33] != null) {

            int maxAmount = context.getPiglinItem().getMaxAmount();

            if (clickType.isLeftClick()) context.getPiglinItem().setMaxAmount(maxAmount + 1);
            else if (clickType.isRightClick() && maxAmount > 1) context.getPiglinItem().setMaxAmount(maxAmount - 1);

            // TODO - update the generator with the piglin item

            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was the back arrow
        if (clickedSlot == 45 && inventoryItems[45] != null) {

            GUIContext listContext = new GUIContext(context.getPiglinItem(), context.getPlayer(), 0);

            return new PiglinItemListGUI(listContext, plugin);

        }

        // Check if the item clicked was the remove button
        if (clickedSlot == 49 && inventoryItems[49] != null) {

            // TODO - remove piglinItem from generator

            GUIContext listContext = new GUIContext(context.getPiglinItem(), context.getPlayer(), 0);

            return new PiglinItemGUI(listContext, plugin);

        }

        return this;

    }
}
