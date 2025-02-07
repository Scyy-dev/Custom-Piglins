package me.Scyy.CustomPiglins.Config;

import me.Scyy.CustomPiglins.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class PlayerMessenger extends ConfigFile {

    private final String prefix;

    /**
     * Constructs a player messenger with the plugin and declares the prefix for messages
     * @param plugin reference to the plugin
     */
    public PlayerMessenger(Plugin plugin) {
        super(plugin, "messages.yml");

        // Get the prefix
        String rawPrefix = config.getString("prefix");
        if (rawPrefix != null) this.prefix = ChatColor.translateAlternateColorCodes('&', rawPrefix);
        else this.prefix = "[COULD_NOT_LOAD_PREFIX]";

    }

    /**
     * For testing if a message exists in config. Intended for testing purposes only
     * @param path The path of the message in messages.yml
     * @return the message, or a default error message if not found
     */
    public String testConfigMessage(String path) {

        String rawMessage = config.getString(path);

        if (rawMessage != null) return ChatColor.translateAlternateColorCodes('&', rawMessage);
        else return "Message not found at: " + path;

    }

    /**
     * For sending a message with parts in the message that get replaced by some variable
     * @param sender The user to send the message to
     * @param path the path of the message in messages.yml
     */
    public void msg(CommandSender sender, String path) {
        this.msg(sender, path, true);

    }

    /**
     * For sending a message with parts in the message that get replaced by some variable
     * @param sender The user to send the message to
     * @param path the path of the message in messages.yml
     * @param usePrefix whether or not to use the prefix
     */
    public void msg(CommandSender sender, String path, boolean usePrefix) {

        this.msg(sender, path, usePrefix, null);

    }

    /**
     * For sending a message with parts in the message that get replaced by some variable
     * @param sender The user to send the message to
     * @param path the path of the message in messages.yml
     * @param usePrefix whether or not to use the prefix
     * @param replacements a map of the identifier for the replacement and the replacement.
     *                     The identifier comes first in the pair
     */
    public void msg(CommandSender sender, String path, boolean usePrefix, Map<String, String> replacements) {

        String rawMessage = config.getString(path);

        if (rawMessage == null) {

            sender.sendMessage("Could not find message at " + path);
            return;

        }

        if (rawMessage.equalsIgnoreCase("")) return;


        if (replacements != null) {

            for (String key : replacements.keySet()) {

                rawMessage = rawMessage.replaceAll(key, replacements.get(key));

            }

        }

        String finalMessage;
        if (usePrefix) {

            finalMessage = prefix + " " + ChatColor.translateAlternateColorCodes('&', rawMessage);

        } else {

            finalMessage = ChatColor.translateAlternateColorCodes('&', rawMessage);

        }
        sender.sendMessage(finalMessage);

    }

}
