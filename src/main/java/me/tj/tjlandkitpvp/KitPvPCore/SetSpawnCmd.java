package me.tj.tjlandkitpvp.KitPvPCore;

import me.tj.tjlandkitpvp.TjLandKit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCmd implements CommandExecutor {

    // Passing the instance plugin instance to this class.
    private final TjLandKit plugin;

    public SetSpawnCmd(TjLandKit plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        // Check if the person who ran the command is a player (You wouldn't want to teleport console to spawn, right?)
        if (sender instanceof Player) {
            // Cast player into sender, so we can use the player object.
            Player player = (Player) sender;

            if (player.hasPermission("tjland.setspawn")) {
                // Get the player's current location
                Location currentLocation = player.getLocation();

               // Prefixes to use in messages, because I'm too lazy to type it everytime.
                String prefix = "&l&7[&cTjLand&7] ";


                // Since we passed in the instance of the plugin we can utilize the config things etc.
                FileConfiguration config = plugin.getConfig();

                // Set the new spawn location in the configuration
                config.set("Spawn.world", currentLocation.getWorld().getName());
                config.set("Spawn.x", currentLocation.getX());
                config.set("Spawn.y", currentLocation.getY());
                config.set("Spawn.z", currentLocation.getZ());
                config.set("Spawn.yaw", currentLocation.getYaw());
                config.set("Spawn.pitch", currentLocation.getPitch());

                // Save the config
                plugin.saveConfig();


                // Send the sender a message that the spawn point has been set successfully.
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Spawn point has been set successfully."));
            } else {
                // If the sender does not have permission to run the command send them a message.

                // Prefixes to use in messages, because I'm too lazy to type it everytime.
                String prefix = "&l&7[&cTjLand&7] ";
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "You do not have permission to run /setspawn."));

            }
        } else {
            String prefix = "[TjLand] ";
            sender.sendMessage(prefix + "Bruh get yo ass on the server.");
        }

        return true;
    }

}
