package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

// # # # # # # # # #
// # # # # P # # # #
// # # # # # # # # #
// # # W D # m M # #
// # # # # # # # # #
// B # # # R # # # C
// P = Piglin Item
// W = Weighting
// D = Has Random Durability
// m = Min Amount
// M = Max Amount
// B = Back to PiglinItemList
// R = Remove Item
// C = Drop Chance

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
        if (itemCanBeDamaged(piglinItem.getItem())) {

            // Toggle the random damage
            if (piglinItem.hasRandomDamage()) {

                damageItemBuilder.lore("&r&7This item will have a random durability!")
                        .enchant();

            }
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
        inventoryItems[45] = new ItemBuilder(Material.BARRIER).name("&6Back").build();

        // Set the remove item
        inventoryItems[49] = new ItemBuilder(Material.TNT).name("&cRemove Item").build();

        // Get the chance of the item being dropped
        double itemChance = plugin.getGenerator().getChance(piglinItem);

        // Round the chance
        double roundedChance = (double) Math.round(itemChance * 10000) / 10000;

        // Add the item
        inventoryItems[53] = new ItemBuilder(Material.NETHER_STAR).name("&6Drop Chance: " + roundedChance).build();

    }

    @Override
    public InventoryGUI handleClick(InventoryClickEvent event) {

        int clickedSlot = event.getRawSlot();
        ClickType clickType = event.getClick();

        // Check if the item clicked was the weight counter
        if (clickedSlot == 29 && inventoryItems[29] != null) {

            int weight = context.getPiglinItem().getWeight();

            if (clickType.isRightClick()) context.getPiglinItem().setWeight(weight + 1);
            else if (clickType.isLeftClick() && weight > 1) context.getPiglinItem().setWeight(weight - 1);

            plugin.getGenerator().updatePiglinItem(context.getPiglinItem(), true);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was the random damage toggle
        if (clickedSlot == 30 && inventoryItems[30] != null
                && itemCanBeDamaged(context.getPiglinItem().getItem())) {

            boolean hasRandomDamage = context.getPiglinItem().hasRandomDamage();

            context.getPiglinItem().setRandomDamage(!hasRandomDamage);

            ItemBuilder rdBuilder = new ItemBuilder(Material.NETHERITE_SHOVEL).name("&6Random Durability");

            if (hasRandomDamage) rdBuilder.lore("&r&7This item will have a random durability!").enchant();
            else rdBuilder.lore("&r&7This item will not have a random durability!");

            rdBuilder.lore("&r&7Click to toggle!");

            inventoryItems[30] = rdBuilder.build();

            plugin.getGenerator().updatePiglinItem(context.getPiglinItem(), false);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was the minimum amount counter
        if (clickedSlot == 32 && inventoryItems[32] != null) {

            int minAmount = context.getPiglinItem().getMinAmount();

            if (clickType.isRightClick() && minAmount < context.getPiglinItem().getMaxAmount() && minAmount < 64) {
                context.getPiglinItem().setMinAmount(minAmount + 1);
            }
            else if (clickType.isLeftClick() && minAmount > 1) context.getPiglinItem().setMinAmount(minAmount - 1);

            plugin.getGenerator().updatePiglinItem(context.getPiglinItem(), false);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was the maximum amount counter
        if (clickedSlot == 33 && inventoryItems[33] != null) {

            int maxAmount = context.getPiglinItem().getMaxAmount();

            if (clickType.isRightClick() && maxAmount < 64) context.getPiglinItem().setMaxAmount(maxAmount + 1);
            else if (clickType.isLeftClick() && maxAmount > context.getPiglinItem().getMinAmount()) {
                context.getPiglinItem().setMaxAmount(maxAmount - 1);
            }

            plugin.getGenerator().updatePiglinItem(context.getPiglinItem(), false);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemGUI(context, plugin);

        }

        // Check if the item clicked was the back arrow
        if (clickedSlot == 45 && inventoryItems[45] != null) {

            GUIContext listContext = new GUIContext(context.getPiglinItem(), context.getPlayer(), 0);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemListGUI(listContext, plugin);

        }

        // Check if the item clicked was the remove button
        if (clickedSlot == 49 && inventoryItems[49] != null) {

            plugin.getGenerator().removePiglinItem(context.getPiglinItem().getItemID());

            GUIContext listContext = new GUIContext(context.getPiglinItem(), context.getPlayer(), 0);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemListGUI(listContext, plugin);

        }

        // Cancel the event
        event.setCancelled(true);

        return this;

    }

    private boolean itemCanBeDamaged(ItemStack itemStack) {

        return EnchantmentTarget.BREAKABLE.includes(itemStack);

    }

}
