package me.tj.tjlandkitpvp.KitPvPManager;

import me.tj.tjlandkitpvp.TjLandKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class KitPvPManagerRegions implements Listener {

    // Pass the instance of the plugin, so we can get the spawn config data.
    private final TjLandKit plugin;

    public KitPvPManagerRegions(TjLandKit plugin) {
        this.plugin = plugin;
    }




    // Defining the region boundaries
    private final int minX = -114;
    private final int maxX = -88;
    private final int minY = 119;
    private final int maxY = 132;
    private final int minZ = -118;
    private final int maxZ = -92;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location toLocation = event.getTo();

        // Check if the player is moving to a different block
        if (toLocation.getBlockX() != event.getFrom().getBlockX() ||
                toLocation.getBlockY() != event.getFrom().getBlockY() ||
                toLocation.getBlockZ() != event.getFrom().getBlockZ()) {

            // Check if the player is leaving the specific rectangle-shaped box region
            if (isLeavingRegion(toLocation)) {
                // Since we passed the instance of the plugin, we can get config data etc.
                FileConfiguration config = plugin.getConfig();




               //Checking if the player does not have a kit on.
               if(!hasKitOn(player)) {

                   // Checking if there is a config section called "Spawn" in the config.yml
                   if (config.isConfigurationSection("Spawn")) {

                       // Prefixes to use in messages, because I'm too lazy to type them everytime.
                       String prefix = "&l&7[&cTjLand&7] ";

                       // Getting the spawn section from the config and retrieving the location data from there.
                       ConfigurationSection spawnSection = config.getConfigurationSection("Spawn");
                       String worldName = spawnSection.getString("world");
                       double x = spawnSection.getDouble("x");
                       double y = spawnSection.getDouble("y");
                       double z = spawnSection.getDouble("z");
                       float yaw = (float) spawnSection.getDouble("yaw");
                       float pitch = (float) spawnSection.getDouble("pitch");

                       // Create the location object from the retrieved data.
                       Location spawnLocation = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);

                       player.teleport(spawnLocation);

                       player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Please equip a kit before leaving spawn!"));


                   } else {
                       Bukkit.getLogger().info("[TjLand] Please set a spawn point, in orded to prevent people from leaving spawn without having a kit on!");
                   }

               }
            }
        }
    }

    private boolean isLeavingRegion(Location location) {
        // Check if the location is outside the defined rectangle-shaped box boundaries
        return location.getX() < minX || location.getX() > maxX ||
                location.getY() < minY || location.getY() > maxY ||
                location.getZ() < minZ || location.getZ() > maxZ;
    }


    private boolean hasKitOn(Player player) {

        // Getting what the player is wearing
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();



        return helmet != null && helmet.getType() != Material.AIR ||
                chestplate != null && chestplate.getType() != Material.AIR ||
                leggings != null && leggings.getType() != Material.AIR ||
                boots != null && boots.getType() != Material.AIR;


    }


}




