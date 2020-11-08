package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.GUI.Suppliers.PiglinItemSupplier;
import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import me.Scyy.CustomPiglins.Piglins.PiglinLootGenerator;
import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

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

public class PiglinItemGUI extends InventoryGUI implements PiglinItemSupplier {

    private PiglinItem piglinItem;

    public PiglinItemGUI(InventoryGUI lastGUI, Plugin plugin) {
        super(lastGUI, plugin);

        // Get the piglinItem
        if (lastGUI instanceof PiglinItemSupplier) {

            this.piglinItem = ((PiglinItemSupplier) lastGUI).supplyPiglinItem();

        } else {

            plugin.getLogger().warning("last GUI could not supply a PiglinItem!");

        }

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

        // Initialise the random enchantment level builder
        ItemBuilder enchantItemBuilder = new ItemBuilder(Material.BOOK).name("&6Random Enchant Levels");

        ItemMeta itemMeta = piglinItem.getItem().getItemMeta();

        // Enchant Item Checker
        boolean hasAnyEnchants = itemMeta != null && itemMeta.hasEnchants();
        boolean hasAnyStoredEnchants = itemMeta instanceof EnchantmentStorageMeta && ((EnchantmentStorageMeta) itemMeta).hasStoredEnchants();

        // Check if the item has random enchantment levels
        if (hasAnyEnchants || hasAnyStoredEnchants) {

            // Toggle the random enchantment levels
            if (piglinItem.hasRandomEnchantLevels()) {

                enchantItemBuilder.lore("&r&7This item will have").lore("&r&7random enchantment levels!")
                        .enchant();

            }
            else enchantItemBuilder.lore("&r&7This item will not have").lore("&r&7random enchantment levels!");

            // Add toggle text
            enchantItemBuilder.lore("&r&7Click to toggle!");

            // Let the user know the item does not support having random enchantment levels
        } else enchantItemBuilder.lore("&r&cThis item does not have any enchantments!");

        // Set the random enchantment item
        inventoryItems[31] = enchantItemBuilder.build();

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
    public InventoryGUI update(InventoryClickEvent event) {

        this.inventoryItems = event.getView().getTopInventory().getContents();

        PiglinLootGenerator generator = plugin.getGenerator();

        if (event.getView().getTopInventory().getHolder() instanceof PiglinItemGUI) {

            // Get the piglin item from the generator
            this.piglinItem = generator.getPiglinItem(piglinItem.getItemID());

        }

        return this.handleClick(event);

    }

    @Override
    public InventoryGUI handleClick(InventoryClickEvent event) {

        int clickedSlot = event.getRawSlot();
        ClickType clickType = event.getClick();

        // Check if the item clicked was the weight counter
        if (clickedSlot == 29 && inventoryItems[29] != null) {

            int weight = piglinItem.getWeight();

            if (clickType.isRightClick()) piglinItem.setWeight(weight + 1);
            else if (clickType.isLeftClick() && weight > 1) piglinItem.setWeight(weight - 1);

            plugin.getGenerator().updatePiglinItem(piglinItem, true);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemGUI(this, plugin);

        }

        // Check if the item clicked was the random damage toggle
        if (clickedSlot == 30 && inventoryItems[30] != null
                && itemCanBeDamaged(piglinItem.getItem())) {

            boolean hasRandomDamage = piglinItem.hasRandomDamage();

            piglinItem.setRandomDamage(!hasRandomDamage);

            ItemBuilder rdBuilder = new ItemBuilder(Material.NETHERITE_SHOVEL).name("&6Random Durability");

            if (hasRandomDamage) rdBuilder.lore("&r&7This item will have a random durability!").enchant();
            else rdBuilder.lore("&r&7This item will not have a random durability!");

            rdBuilder.lore("&r&7Click to toggle!");

            inventoryItems[30] = rdBuilder.build();

            plugin.getGenerator().updatePiglinItem(piglinItem, false);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemGUI(this, plugin);

        }

        // Check if the item clicked was the random enchantment level toggle
        if (clickedSlot == 31 && inventoryItems[31] != null) {

            ItemMeta itemMeta = piglinItem.getItem().getItemMeta();

            boolean hasAnyEnchants = itemMeta != null && itemMeta.hasEnchants();

            boolean hasAnyStoredEnchants = itemMeta instanceof EnchantmentStorageMeta && ((EnchantmentStorageMeta) itemMeta).hasStoredEnchants();

            // Check if the item has normal enchants or has stored enchants
            if (hasAnyEnchants || hasAnyStoredEnchants) {

                boolean hasRandomEnchantmentLevels = piglinItem.hasRandomEnchantLevels();

                piglinItem.setRandomEnchantLevels(!hasRandomEnchantmentLevels);

                ItemBuilder relBuilder = new ItemBuilder(Material.BOOK).name("&6Random Enchant Levels");

                if (hasRandomEnchantmentLevels) relBuilder.lore("&r&7This item will have")
                        .lore("&r&7random enchant levels!").enchant();
                else relBuilder.lore("&r&7This item will not have").lore("&r&7random enchantment levels!");

                relBuilder.lore("&r&7Click to toggle!");

                inventoryItems[31] = relBuilder.build();

                plugin.getGenerator().updatePiglinItem(piglinItem, false);

                // Cancel the event
                event.setCancelled(true);

                return new PiglinItemGUI(this, plugin);

            }

        }

        // Check if the item clicked was the minimum amount counter
        if (clickedSlot == 32 && inventoryItems[32] != null) {

            int minAmount = piglinItem.getMinAmount();

            int providedItemMaxAmount = piglinItem.getItem().getMaxStackSize();

            if (clickType.isRightClick() && minAmount < piglinItem.getMaxAmount() && minAmount < providedItemMaxAmount) {
                piglinItem.setMinAmount(minAmount + 1);
            }
            else if (clickType.isLeftClick() && minAmount > 1) piglinItem.setMinAmount(minAmount - 1);

            plugin.getGenerator().updatePiglinItem(piglinItem, false);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemGUI(this, plugin);

        }

        // Check if the item clicked was the maximum amount counter
        if (clickedSlot == 33 && inventoryItems[33] != null) {

            int maxAmount = piglinItem.getMaxAmount();

            int providedItemMaxAmount = piglinItem.getItem().getMaxStackSize();

            if (clickType.isRightClick() && maxAmount < providedItemMaxAmount) piglinItem.setMaxAmount(maxAmount + 1);
            else if (clickType.isLeftClick() && maxAmount > piglinItem.getMinAmount()) {
                piglinItem.setMaxAmount(maxAmount - 1);
            }

            plugin.getGenerator().updatePiglinItem(piglinItem, false);

            // Cancel the event
            event.setCancelled(true);

            return new PiglinItemGUI(this, plugin);

        }

        // Check if the item clicked was the back arrow
        if (clickedSlot == 45 && inventoryItems[45] != null) {

            // Cancel the event
            event.setCancelled(true);

            // Mark the inventory to be reopened
            this.reopen = true;

            return new PiglinItemListGUI(this, plugin);

        }

        // Check if the item clicked was the remove button
        if (clickedSlot == 49 && inventoryItems[49] != null) {

            plugin.getGenerator().removePiglinItem(piglinItem.getItemID());

            // Cancel the event
            event.setCancelled(true);

            // Mark the inventory to be reopened
            this.reopen = true;

            return new PiglinItemListGUI(this, plugin);

        }

        // Cancel the event
        event.setCancelled(true);

        return new PiglinItemGUI(this, plugin);

    }

    @Override
    public boolean allowPlayerInventoryEdits() {
        return false;
    }

    private boolean itemCanBeDamaged(ItemStack itemStack) {

        return EnchantmentTarget.BREAKABLE.includes(itemStack);

    }

    @Override
    public PiglinItem supplyPiglinItem() {
        return piglinItem;
    }
}
