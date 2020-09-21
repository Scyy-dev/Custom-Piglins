package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryGUI {

    public static final String INVENTORY_NAME = ChatColor.translateAlternateColorCodes('&', "&6&lCustom&8&lPiglins");

    /**
     * The page number of custom trades. Pages start at 0
     */
    protected int page;

    /**
     * The player that can be messaged to open this inventory with
     */
    protected Player player;

    /**
     * The inventory to display to the player
     */
    protected final ItemStack[] inventoryItems;

    /**
     * The inventory to be displayed to the user
     */
    protected final Inventory inventory;

    public InventoryGUI(int page, Player player, Plugin plugin) {

        this.page = page;
        this.player = player;
        this.inventoryItems = initaliseDefaultPage();
        this.inventory = Bukkit.createInventory(null, 54, INVENTORY_NAME);

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



    // Getters and Setters

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
