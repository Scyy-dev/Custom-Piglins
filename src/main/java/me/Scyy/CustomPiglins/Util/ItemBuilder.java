package me.Scyy.CustomPiglins.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    /**
     * This final item to be built
     */
    private final ItemStack item;

    /**
     * Meta for the item. Stores Display Name and Lore
     */
    private final ItemMeta itemMeta;

    /**
     * Lore for the item. An incremental list that gets added to by the builder
     */
    private final List<String> itemLore;

    /**
     * Creates the initial ItemStack
     * @param material the material for the ItemStack
     */
    public ItemBuilder(Material material) {

        this.item = new ItemStack(material, 1);
        this.itemMeta = item.getItemMeta();
        this.itemLore = new ArrayList<>();

    }


    /*  Material and Amount  */
    /**
     * Add an amount to the ItemStack
     * @param amount the amount to be added
     * @return The Builder instance
     */
    public ItemBuilder amount(int amount) {

        item.setAmount(amount);
        return this;

    }

    /**
     * Add a material to the ItemStack
     * @param material the material to be added
     * @return The Builder instance
     */
    public ItemBuilder type(Material material) {

        item.setType(material);
        return this;

    }

    /*  Name and Lore  */
    /**
     * Add a name to the ItemStack. Uses '&' for minecraft colour formatting
     * @param name the name to be added
     * @return The Builder instance
     */
    public ItemBuilder name(String name) {

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;

    }

    /**
     * Add a single line of lore to the ItemStack. Uses '&' for minecraft colour formatting
     * @param lore the lore to be added
     * @return The Builder instance
     */
    public ItemBuilder lore(String lore) {

        itemLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        return this;

    }

    /**
     * Add multiple lines of lore to the ItemStack. Uses '&' for minecraft colour formatting
     * @param lore the lore to be added
     * @return The Builder instance
     */
    public ItemBuilder lore(String... lore) {

        for (String loreLine : lore) {

            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));

        }

        return this;

    }

    /*  Enchanting  */
    /**
     * Gives the item the enchanted look but hides the enchantment names
     * @return The Builder instance
     */
    public ItemBuilder enchant() {

        item.addEnchantment(Enchantment.MENDING, 1);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;

    }

    /**
     * Add an enchant to the ItemStack
     * @param enchantment the enchantment to be added
     * @param level the level of the enchant
     * @return The Builder instance
     */
    public ItemBuilder enchant(Enchantment enchantment, int level) {

        item.addUnsafeEnchantment(enchantment, level);
        return this;

    }

    /**
     * Sets the item to be unbreakble
     * @param showFlag whether to show that the item is unbreakable
     * @return The Builder instance
     */
    public ItemBuilder unbreakable(boolean showFlag) {

        itemMeta.setUnbreakable(true);
        if (showFlag) itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;

    }

    /**
     * Builds the item
     * @return the item
     */
    public ItemStack build() {

        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;

    }

}
