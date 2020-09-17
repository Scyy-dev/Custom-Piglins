package me.Scyy.CustomPiglins;

import me.Scyy.CustomPiglins.Config.PlayerMessenger;
import me.Scyy.CustomPiglins.GUI.CustomPiglinGUI;
import me.Scyy.CustomPiglins.Piglins.CustomPiglinLootGenerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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

                Player player = (Player) sender;
                new CustomPiglinGUI(0, player);

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
