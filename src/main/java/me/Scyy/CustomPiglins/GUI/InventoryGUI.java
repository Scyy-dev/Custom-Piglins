package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class InventoryGUI implements InventoryHolder {

    public static final String INVENTORY_NAME = ChatColor.translateAlternateColorCodes('&', "&6&lCustom&8&lPiglins");

    /**
     * Context for the inventory being constructed
     */
    protected GUIContext context;

    /**
     * Main plugin reference
     */
    protected final Plugin plugin;

    /**
     * The array of items in the inventory
     */
    protected final ItemStack[] inventoryItems;

    /**
     * The inventory to be displayed to the user
     */
    protected final Inventory inventory;

    public InventoryGUI(GUIContext context, Plugin plugin) {

        this.context = context;
        this.plugin = plugin;
        this.inventoryItems = initaliseDefaultPage();
        this.inventory = Bukkit.createInventory(this, 54, INVENTORY_NAME);

    }

    /**
     * Creates a double chest full of nameless black stained glass panes
     * @return the itemstack array with the glass panes
     */
    private ItemStack[] initaliseDefaultPage() {

        ItemStack[] defaultInv = new ItemStack[54];
        for (int i = 0; i < 54; i++) {

            ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            ItemMeta glassMeta = blackGlass.getItemMeta();
            glassMeta.setDisplayName("");
            blackGlass.setItemMeta(glassMeta);
            defaultInv[i] = blackGlass;

        }

        return defaultInv;

    }

    public abstract InventoryGUI handleClick(int clickedSlot, ClickType clickType, ItemStack cursorItem);

    // Getters and Setters

    public GUIContext getContext() {
        return context;
    }

    public void setContext(GUIContext context) {
        this.context = context;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public ItemStack[] getInventoryItems() {
        return inventoryItems;
    }

    public Inventory getInventory() {

        // Assign the inventory items to the inventory
        inventory.setContents(inventoryItems);

        // Return the inventory
        return inventory;
    }
}
