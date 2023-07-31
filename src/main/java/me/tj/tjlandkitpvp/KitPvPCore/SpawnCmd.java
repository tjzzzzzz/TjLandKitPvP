package me.tj.tjlandkitpvp.KitPvPCore;

import me.tj.tjlandkitpvp.TjLandKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnCmd implements CommandExecutor {

    // Passing the instance plugin instance to this class.
    private final TjLandKit plugin;

    public SpawnCmd(TjLandKit plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        // Check if the person who ran the command is a player.
        if (sender instanceof Player) {

            // Cast player into sender, so we can use the player object.
            Player player = (Player) sender;

            if (player.hasPermission("tjland.spawn")) {

                // Since we passed the instance of the plugin, we can get config data etc.
                FileConfiguration config = plugin.getConfig();

                // Check if the "Spawn" section exists in the configuration.
                if (config.isConfigurationSection("Spawn")) {



                    // Prefixes to use in messages, because I'm too lazy to type it everytime.
                    String prefix = "&l&7[&cTjLand&7] ";

                    // Get the player name
                    String senderName = player.getDisplayName();

                    // Get the "Spawn" section and retrieve the location data.
                    ConfigurationSection spawnSection = config.getConfigurationSection("Spawn");
                    String worldName = spawnSection.getString("world");
                    double x = spawnSection.getDouble("x");
                    double y = spawnSection.getDouble("y");
                    double z = spawnSection.getDouble("z");
                    float yaw = (float) spawnSection.getDouble("yaw");
                    float pitch = (float) spawnSection.getDouble("pitch");

                    // Create the location object from the retrieved data.
                    Location spawnLocation = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);

                    // Teleport the player to the spawn location.
                    player.teleport(spawnLocation);

                    // Send the player a message that they've been teleported.
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "You've been teleported to spawn."));

                    // Log the action, because why not.
                    Bukkit.getLogger().info("[TjLand] " + senderName + " Teleported to spawn using /spawn.");
                } else {
                    // Prefixes to use in messages, because I'm too lazy to type it everytime.
                    String prefix = "&l&7[&cTjLand&7] ";

                    // If there is no set location send them a message.
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "There is not set spawn point, run /setspawn to set it."));
                }
            } else {
                // Prefixes to use in messages, because I'm too lazy to type it everytime.
                String prefix = "&l&7[&cTjLand&7] ";
                // If they do not have permission to run /spawn, send them a message.
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "You do not have permission to run /spawn."));
            }
        } else {

            // If the sender isn't a player object send them a message.
            sender.sendMessage("[TjLand] Bruh get yo ass on the server.");
        }

        return true;
    }

}

