package me.tj.tjlandkitpvp.KitPvPCore;

import me.tj.tjlandkitpvp.TjLandKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class CoreEvents implements Listener {

    private final TjLandKit plugin;

    public CoreEvents(TjLandKit plugin){
        this.plugin = plugin;
    }

    String prefix = "&l&7[&cTjLand&7] ";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getDisplayName();

        player.setSaturation(10.0F);


        e.setJoinMessage("");

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + playerName + " has joined the server!"));

        if(!player.hasPlayedBefore()) {

            // Prefixes to use in messages, because I'm too lazy to type it everytime.
            String prefix = "&l&7[&cTjLand&7] ";

            // Send the player a little welcome message.
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Welcome to TjLand KitPvP, if you have any questions feel free to ask or run /help!"));

            // Since we passed the instance of the plugin, we can get config data etc.
            FileConfiguration config = plugin.getConfig();

            // Check if the "Spawn" section exists in the configuration.
            if (config.isConfigurationSection("Spawn")) {


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
                Bukkit.getLogger().info("[TjLand] " + playerName + " Teleported to spawn, because it's their first time joining.");
            } else {

                // If there is no set spawn location log it in console.
                Bukkit.getLogger().info("[TjLand] There is no spawn point set, so new players will not get teleported to spawn on join!");


            }



        }



    }


    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {

   e.setCancelled(true);

    }




}
