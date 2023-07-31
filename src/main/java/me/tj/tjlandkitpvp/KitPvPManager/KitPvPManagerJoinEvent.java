package me.tj.tjlandkitpvp.KitPvPManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;


public class KitPvPManagerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if the player's hotbar slots are empty, because i did not come up with a better method to prevent people from joining and leaving in pvp and getting kit selector items etc.
        boolean hotbarIsEmpty = true;
        for (int slot = 0; slot < 9; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                hotbarIsEmpty = false;
                break;
            }
        }

        // If hotbar slots are empty, give the player specific items
        if (hotbarIsEmpty) {


            ItemStack kitSelector = createKitSelector(player);
            player.getInventory().setItem(0, kitSelector);

        }
    }


    // Method to create kitSelector, this is to prevent having like 400 lines of code in the if(hotbarisempty) check. Just makes everything more clean tbh.
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
}

