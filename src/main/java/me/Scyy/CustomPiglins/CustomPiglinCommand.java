package me.Scyy.CustomPiglins;

import me.Scyy.CustomPiglins.Config.PlayerMessenger;
import me.Scyy.CustomPiglins.GUI.InventoryGUI;
import me.Scyy.CustomPiglins.GUI.PiglinItemListGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomPiglinCommand implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final PlayerMessenger pm;

    public CustomPiglinCommand(Plugin plugin) {

        this.plugin = plugin;
        this.pm = plugin.getConfigFileHandler().getPlayerMessenger();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 0) {

            if (!sender.hasPermission("custompiglins.gui")) {
                pm.msg(sender, "errorMessages.noPermission", false);
                return true;
            }
            if (!(sender instanceof Player)) {
                pm.msg(sender, "errorMessages.mustBePlayer", true);
                return true;
            }

            Player player = (Player) sender;
            InventoryGUI inventoryGUI = new PiglinItemListGUI(null, plugin);
            player.openInventory(inventoryGUI.getInventory());
            return true;

        }

        switch (args[0].toLowerCase()) {

            // /custompiglins converter [consumable | non-consumable] [player]
            case "converter":
                converterSubcommand(sender, args);
                return true;

            // /custompiglins reload
            case "reload":

                if (!sender.hasPermission("custompiglins.reload")) {
                    pm.msg(sender, "errorMessages.noPermission", false);
                    return true;
                }
                plugin.reload(sender);
                return true;

            default:

                pm.msg(sender, "errorMessages.invalidCommand");
                return true;

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length == 1) {

            List<String> list = new ArrayList<>();

            if (commandSender.hasPermission("custompiglins.converter.give")) list.add("converter");
            if (commandSender.hasPermission("custompiglins.reload")) list.add("reload");

            return list;

        }

        if (args[0].equals("converter")) {
            if (!commandSender.hasPermission("custompiglins.converter.give")) return null;
            if (args.length == 2) return Arrays.asList("consumable", "non-consumable");
            return null;
        }
        return null;

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
                pm.msg(target, "converterMessages.receivedConsumable");
                return;

            case "non-consumable":

                target.getInventory().addItem(plugin.getConfigFileHandler().getDefaultConfig().getNonConsumableConverter());
                pm.msg(target, "converterMessages.receivedNonConsumable");
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
