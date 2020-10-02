package me.Scyy.CustomPiglins;

import me.Scyy.CustomPiglins.Config.PlayerMessenger;
import me.Scyy.CustomPiglins.GUI.GUIContext;
import me.Scyy.CustomPiglins.GUI.InventoryGUI;
import me.Scyy.CustomPiglins.GUI.PiglinItemListGUI;
import me.Scyy.CustomPiglins.Piglins.PiglinLootGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomPiglinCommand implements CommandExecutor, TabCompleter {

    private final PiglinLootGenerator lootGenerator;
    private final Plugin plugin;
    private final PlayerMessenger pm;

    public CustomPiglinCommand(PiglinLootGenerator lootGenerator, Plugin plugin) {

        this.lootGenerator = lootGenerator;
        this.plugin = plugin;
        this.pm = plugin.getConfigFileHandler().getPlayerMessenger();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 0) {

            if (validate(sender.hasPermission("custompiglins.gui"), "errorMessages.noPermission", sender)) return true;
            if (validate(sender instanceof Player, "errorMessages.mustBePlayer", sender)) return true;

            Player player = (Player) sender;
            InventoryGUI inventoryGUI = new PiglinItemListGUI(new GUIContext(null, player, 0), plugin);
            player.openInventory(inventoryGUI.getInventory());
            return true;

        }

        switch (args[0].toLowerCase()) {

            // /custompiglins add
            case "add":

                if (validate(sender.hasPermission("custompiglins.add"), "errorMessages.noPermission", sender)) return true;
                if (validate(sender instanceof Player, "errorMessages.mustBePlayer", sender)) return true;

                addSubcommand((Player) sender, args);
                return true;

            // /custompiglins converter [consumable | non-consumable] [player]
            case "converter":

                if (validate(sender.hasPermission("custompiglins.converter.give"), "errorMessages.noPermission", sender)) return true;
                if (validate(args.length == 3, "errorMessages.invalidCommandLength", sender)) return true;
                if (validate(Bukkit.getPlayer(args[2]) != null, "errorMessages.playerNotFound", sender)) return true;

                converterSubcommand(sender, args);
                return true;

            // /custompiglins reload
            case "reload":

                try {

                    plugin.reloadConfigs();

                } catch (Exception e) {

                    sender.sendMessage("Could not reload configs! Check Console for error!");
                    e.printStackTrace();

                }

                return true;

            default:

                pm.msg(sender, "errorMessages.invalidCommand");
                return true;

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender.hasPermission("customPiglins.use")) {

            return Arrays.asList("add", "reload", "converter");

        } else {

            return Collections.singletonList("");

        }
    }

    private void addSubcommand(Player player, String[] args) {

        if (!player.hasPermission("custompiglins.add")) {

            pm.msg(player, "errorMessages.noPermission");
            return;

        }

        // Get the item
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if (mainHand.getType() == Material.AIR) {

            pm.msg(player, "errorMessages.cannotDropAir");
            return;

        }

        ItemStack item = mainHand.clone();
        item.setAmount(1);

        plugin.getGenerator().addPiglinItem(item, 1, 1, 1, false, false);

        player.sendMessage("Added " + item.getType().name().toLowerCase());

    }

    private void converterSubcommand(CommandSender sender, String[] args) {

        Player target = Bukkit.getPlayer(args[2]);

        if (target == null) {

            pm.msg(sender, "errorMessages.playerNotFound");
            return;

        }

        switch (args[1].toLowerCase()) {

            case "consumable":

                target.getInventory().addItem(plugin.getConfigFileHandler().getDefaultConfig().getConsumableConverter());
                return;

            case "non-consumable":

                target.getInventory().addItem(plugin.getConfigFileHandler().getDefaultConfig().getNonConsumableConverter());
                return;

            default:

                pm.msg(sender, "errorMessages.invalidCommand");

        }

    }

    private boolean validate(boolean condition, String errorMessagePath, CommandSender sender) {

        if (!condition) {

            pm.msg(sender, errorMessagePath);
            return true;

        } else {

            return false;

        }

    }

}
