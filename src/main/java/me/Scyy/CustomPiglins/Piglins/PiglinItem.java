package me.Scyy.CustomPiglins.Piglins;

import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PiglinItem {

    private ItemStack item;

    private int itemID;

    private int weight;

    private int minAmount;

    private int maxAmount;

    private boolean randomDamage;

    private boolean randomEnchantLevels;

    public PiglinItem(ItemStack item, int itemID, int weight, int minAmount, int maxAmount, boolean randomDamage, boolean randomEnchantLevels) {
        this.item = item;
        this.itemID = itemID;
        this.weight = weight;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.randomDamage = randomDamage;
        this.randomEnchantLevels = randomEnchantLevels;
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

    public boolean hasRandomDamage() {
        return randomDamage;
    }

    public void setRandomDamage(boolean randomDamage) {
        this.randomDamage = randomDamage;
    }

    public boolean hasRandomEnchantLevels() {
        return randomEnchantLevels;
    }

    public void setRandomEnchantLevels(boolean randomEnchantLevels) {
        this.randomEnchantLevels = randomEnchantLevels;
    }

    @Override
    public String toString() {
        return "PiglinItem{" +
                "item=" + item +
                ", itemID=" + itemID +
                ", weight=" + weight +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                ", hasRD=" + randomDamage +
                ", hasREL=" + randomEnchantLevels +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiglinItem that = (PiglinItem) o;
        return itemID == that.itemID &&
                weight == that.weight &&
                minAmount == that.minAmount &&
                maxAmount == that.maxAmount &&
                randomDamage == that.randomDamage &&
                randomEnchantLevels == that.randomEnchantLevels &&
                item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, itemID, weight, minAmount, maxAmount, randomDamage, randomEnchantLevels);
    }
}
