package me.Scyy.CustomPiglins.GUI;

import me.Scyy.CustomPiglins.Piglins.PiglinItem;
import org.bukkit.entity.Player;

public class GUIContext {

    private PiglinItem piglinItem;

    private Player player;

    private int page;

    public GUIContext(PiglinItem piglinItem, Player player, int page) {
        this.piglinItem = piglinItem;
        this.player = player;
        this.page = page;
    }

    public PiglinItem getPiglinItem() {
        return piglinItem;
    }

    public void setPiglinItem(PiglinItem piglinItem) {
        this.piglinItem = piglinItem;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
