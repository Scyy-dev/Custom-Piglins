package me.Scyy.CustomPiglins.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomPiglinGUI {

    public static final String INVENTORY_NAME = ChatColor.translateAlternateColorCodes('&', "&6Custom&2Piglins");

    /**
     * Stores a reference to the page number of custom trades. Page 0 is settings
     */
    private int page;

    private Player player;

    private static final ItemStack[] DEFAULT_CUSTOM_TRADE_PAGE = initaliseDefaultPage();

    private static final ItemStack[] DEFAULT_SETTINGS_PAGE = initaliseDefaultPage();

    public CustomPiglinGUI(int page, Player player) {

        this.page = page;
        this.player = player;

        Inventory inventory = Bukkit.createInventory(null, 54, INVENTORY_NAME);
        inventory.setContents(DEFAULT_CUSTOM_TRADE_PAGE);
        player.openInventory(inventory);

    }

    /**
     * Creates a double chest full of nameless black stained glass panes
     * @return the itemstack array with the glass panes
     */
    private static ItemStack[] initaliseDefaultPage() {

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

}
