package me.Scyy.CustomPiglins;

import me.Scyy.CustomPiglins.Config.PlayerMessenger;
import me.Scyy.CustomPiglins.GUI.GUIContext;
import me.Scyy.CustomPiglins.GUI.InventoryGUI;
import me.Scyy.CustomPiglins.GUI.PiglinItemListGUI;
import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomPiglinCommand implements CommandExecutor, TabCompleter {

    private final CustomPiglinLootGenerator lootGenerator;
    private final Plugin plugin;
    private final PlayerMessenger pm;

    public CustomPiglinCommand(CustomPiglinLootGenerator lootGenerator, Plugin plugin) {

        this.lootGenerator = lootGenerator;
        this.plugin = plugin;
        this.pm = plugin.getConfigFileHandler().getPlayerMessenger();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender.hasPermission("customPiglins.command")) {

            if (sender instanceof Player) {

                if (args.length == 0) {

                    Player player = (Player) sender;
                    InventoryGUI inventoryGUI = new PiglinItemListGUI(new GUIContext(null, player, 0), plugin);
                    player.openInventory(inventoryGUI.getInventory());
                    return true;

                }

                switch (args[0].toLowerCase()) {

                    case "add":

                        Player player = (Player) sender;

                        ItemStack mainHand = player.getInventory().getItemInMainHand();

                        if (mainHand.getType() == Material.AIR) {

                            player.sendMessage("Cannot add air!");
                            return true;

                        }

                        plugin.getGenerator().addPiglinItem(mainHand, 1, 1, 1, false);

                        player.sendMessage("Added " + mainHand.getType().name().toLowerCase());

                }

            } else {

                pm.msg(sender, "errorMessages.mustBePlayer");

            }

        } else {

            pm.msg(sender, "errorMessages.noPermission");

        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return null;
    }
}
