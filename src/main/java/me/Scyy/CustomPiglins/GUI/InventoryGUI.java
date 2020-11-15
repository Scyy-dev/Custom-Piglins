package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Plugin;
import me.Scyy.CustomPiglins.Util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class InventoryGUI implements InventoryHolder {

    public static final String INVENTORY_NAME = ChatColor.translateAlternateColorCodes('&', "&6&lCustom&8&lPiglins");

    /**
     * A reference to the GUI before this one. Null if the GUI system was just opened
     */
    protected InventoryGUI lastGUI;

    /**
     * Reference to the player associated with this GUI
     */
    protected Player player;

    /**
     * Main plugin reference
     */
    protected final Plugin plugin;

    /**
     * The array of items in the inventory
     */
    protected ItemStack[] inventoryItems;

    /**
     * The inventory to be displayed to the user
     */
    protected final Inventory inventory;

    /**
     * Flag for if the new inventory needs to be reopened
     */
    protected boolean reopen = false;

    public InventoryGUI(InventoryGUI lastGUI, Plugin plugin, InventoryType type) {

        this.lastGUI = lastGUI;
        this.plugin = plugin;
        this.inventoryItems = new ItemStack[type.getDefaultSize()];
        this.inventory = Bukkit.createInventory(this, InventoryType.ANVIL, INVENTORY_NAME);

    }

    public InventoryGUI(InventoryGUI lastGUI, Plugin plugin, int size) {

        this.lastGUI = lastGUI;
        this.plugin = plugin;
        this.inventoryItems = initialiseDefaultPage(size);
        this.inventory = Bukkit.createInventory(this, size, INVENTORY_NAME);


    }

    /**
     * Creates a double chest full of nameless black stained glass panes
     * @return the itemstack array with the glass panes
     */
    private ItemStack[] initialiseDefaultPage(int size) {

        ItemStack[] defaultInv = new ItemStack[size];
        for (int i = 0; i < size; i++) {
            defaultInv[i] = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("").build();
        }

        return defaultInv;

    }

    public abstract InventoryGUI handleClick(InventoryClickEvent event);

    public abstract InventoryGUI update(InventoryClickEvent event);

    // Getters and Setters
    public InventoryGUI getLastGUI() {
        return lastGUI;
    }

    public void setLastGUI(InventoryGUI lastGUI) {
        this.lastGUI = lastGUI;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public boolean shouldReopen() {
        return reopen;
    }

    public void setReopen(boolean reopen) {
        this.reopen = reopen;
    }

    public abstract boolean allowPlayerInventoryEdits();

}
