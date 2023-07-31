package me.tj.tjlandkitpvp.KitPvPKits;

import me.tj.tjlandkitpvp.TjLandKit;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.ArrayList;



public class kitSelectorGui implements Listener {





    private final TjLandKit plugin;

    public kitSelectorGui(TjLandKit plugin) {
        this.plugin = plugin;
    }





    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        // Get the player that was involved in this event.
        Player player = event.getPlayer();

        // Get their item in main hand
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Check if the player is right-clicking and holding a diamond sword
        if (event.getAction().name().contains("RIGHT_CLICK") && itemInHand.getType() == Material.DIAMOND_SWORD) {
            // Check if the diamond sword is named "&1&lKit Selector"
            if (itemInHand.getItemMeta().hasDisplayName() &&
                    itemInHand.getItemMeta().getDisplayName().equals(ChatColor.DARK_BLUE + ChatColor.BOLD.toString() + "Kit Selector")) {

                // If all this is returning true, open up our custom gui.

                // Create the inventory, with our "createKitSelectorInventory" method.
                Inventory kitSelectorGui = createKitSelectorInventory(player);

                // Open that inventory.
                player.openInventory(kitSelectorGui);

            }
        }
    }

    private Inventory createKitSelectorInventory(Player player) {

        // Create the kitSelector Inventory
        String invName = "§1§lKit Selector Menu";
        Inventory kitselectorguiInv = Bukkit.createInventory(player, 9, ChatColor.translateAlternateColorCodes('&', invName));


        // Create the PvP Kit
        String pvpKitName = "&f&lPvP Kit";
        ItemStack pvpKit = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta pvpKitMeta = pvpKit.getItemMeta();
        pvpKitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', pvpKitName));
        ArrayList<String> pvpkitLore = new ArrayList<>();
        pvpkitLore.add("§7Default PvP Kit");
        pvpkitLore.add("§c§lClick to equip");
        pvpKitMeta.setLore(pvpkitLore);
        pvpKit.setItemMeta(pvpKitMeta);





        // Set the kits into the inventory.
        kitselectorguiInv.setItem(4, pvpKit);



        // Return our inventory.
        return kitselectorguiInv;
    }


    // The event/method to handle kit selections within the menu
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        // Check if the clicked inventory is the kit selector menu/gui.
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "§1§lKit Selector Menu"))) {

            // Cancel the event to prevent players from taking items from the kit selector menu/gui.
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();

            // Check if the clicked item is the PvP Kit

            String pvpKitName = ChatColor.translateAlternateColorCodes('&', "&f&lPvP Kit");
            if (clickedItem != null && clickedItem.getType() == Material.IRON_CHESTPLATE
                    && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', pvpKitName)) ) {

                        // Set the player's armor to iron armor.
                        ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET);
                        ItemStack ironChestplate = new ItemStack(Material.IRON_CHESTPLATE);
                        ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS);
                        ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);
                        player.getInventory().setHelmet(ironHelmet);
                        player.getInventory().setChestplate(ironChestplate);
                        player.getInventory().setLeggings(ironLeggings);
                        player.getInventory().setBoots(ironBoots);



                      // Give them a normal diamond sword and remove the kit selector sword by just replacing it.
                       ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
                      player.getInventory().setItem(0, diamondSword);

                // Add a full inventory of Instant Health II potions
                ItemStack instantHealthPotion = new ItemStack(Material.SPLASH_POTION, 1);

                // Set the potion type to Instant Health II
                PotionMeta potionMeta = (PotionMeta) instantHealthPotion.getItemMeta();
                potionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
                instantHealthPotion.setItemMeta(potionMeta);


                // Loop through all slots except the first slot (0) and the offhand slot (-106) and add the potions
                for (int i = 1; i < player.getInventory().getSize(); i++) {
                    if (i != 0 && i != 40 && player.getInventory().getItem(i) == null) {
                        player.getInventory().setItem(i, instantHealthPotion);
                    }
                }

                        player.updateInventory();
                        player.sendMessage(ChatColor.GREEN + "You have equipped the PvP Kit!");

            }
        }
    }


    // Now we have to code that part, that if the player dies he gets teleported to spawn and gets given the kit selector again.
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

    Player player = e.getEntity();

    // Clear drops when a player dies.
    e.getDrops().clear();


// Respawn the player automatically to prevent the player having to click respawn in the menu.
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(plugin, 0);
    }





    // Method to create kitSelector,
    private ItemStack createKitSelector(Player player) {

        ItemStack kitSelector = new ItemStack(Material.DIAMOND_SWORD, 1);


        ItemMeta kitSelectorMeta = kitSelector.getItemMeta();


        String kitSelectorName = "&1&lKit Selector";


        kitSelectorMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', kitSelectorName));

        kitSelectorMeta.setUnbreakable(true);


        ArrayList<String> kitSelectorLore = new ArrayList<>();

        kitSelectorLore.add(ChatColor.translateAlternateColorCodes('&', "&7Right click with this item, to choose a kit."));
        kitSelectorLore.add(ChatColor.translateAlternateColorCodes('&', "&7Need help, run /help!"));



        kitSelectorMeta.setLore(kitSelectorLore);

        kitSelector.setItemMeta(kitSelectorMeta);



        return kitSelector;
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        // Getting our config.
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
            e.setRespawnLocation(spawnLocation);

            // Send the player a message that they've been teleported.
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&7[&cTjLand&7] You've been teleported to spawn."));


            ItemStack kitSelector = createKitSelector(player);
            player.getInventory().setItem(0, kitSelector);


            // Log the action, because why not.
            Bukkit.getLogger().info("[TjLand] " + player.getDisplayName() + " Teleported to spawn because they died.");
        } else {
            // Log it into console that there is no spawn point set.
            Bukkit.getLogger().info("[TjLand] There is no spawn point set, so we cannot teleport a player when they die to spawn. Set a spawn point by doing /setspawn.");
        }
    }




}



