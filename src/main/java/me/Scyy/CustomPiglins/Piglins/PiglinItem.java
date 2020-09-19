package me.Scyy.CustomPiglins.Piglins;

import org.bukkit.inventory.ItemStack;

public class PiglinItem {

    private ItemStack item;

    private int itemID;

    private int weight;

    private int minAmount;

    private int maxAmount;

    public PiglinItem(ItemStack item, int itemID, int weight, int minAmount, int maxAmount) {
        this.item = item;
        this.itemID = itemID;
        this.weight = weight;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public String toString() {
        return "PiglinItem{" +
                "item=" + item +
                ", itemID=" + itemID +
                ", weight=" + weight +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                '}';
    }
}
