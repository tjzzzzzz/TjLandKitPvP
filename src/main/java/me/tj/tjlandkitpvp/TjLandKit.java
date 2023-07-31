package me.tj.tjlandkitpvp;

import me.tj.tjlandkitpvp.KitPvPCore.CoreEvents;
import me.tj.tjlandkitpvp.KitPvPCore.SetSpawnCmd;
import me.tj.tjlandkitpvp.KitPvPCore.SpawnCmd;
import me.tj.tjlandkitpvp.KitPvPKits.kitSelectorGui;
import me.tj.tjlandkitpvp.KitPvPManager.KitPvPManagerJoinEvent;
import me.tj.tjlandkitpvp.KitPvPManager.KitPvPManagerRegions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public final class TjLandKit extends JavaPlugin {



    @Override
    public void onEnable() {

        // Log into console that this masterpiece has loaded "Successfully"
        Bukkit.getLogger().info("TjLandKit Loaded");



        // Register events
        getServer().getPluginManager().registerEvents(new CoreEvents(this), this);
        getServer().getPluginManager().registerEvents(new KitPvPManagerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new kitSelectorGui(this), this);
        getServer().getPluginManager().registerEvents(new KitPvPManagerRegions(this),this);



        // Register commands
        getCommand("setspawn").setExecutor(new SetSpawnCmd(this));
        getCommand("spawn").setExecutor(new SpawnCmd(this));



        Bukkit.getScheduler().runTaskTimer(this, this::updateScoreboard, 0, 20);

    }


    @Override
    public void onDisable() {

        Bukkit.getLogger().info("TjLandKit UnLoaded");



    }





    private void createScoreboard(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if (scoreboardManager == null) {
            // ScoreboardManager is not available, cannot create scoreboard
            return;
        }

        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

        // Create the objective and set its display name.
        Objective objective = scoreboard.registerNewObjective("TjLand", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.setDisplayName("      §c§lTjLand      ");

        // Add the text and whatnot to the scoreboard.
        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        objective.getScore(ChatColor.WHITE + " ").setScore(6);
        objective.getScore(ChatColor.GRAY + "Players: " + onlinePlayers).setScore(5);
        objective.getScore(ChatColor.GOLD + "Your stats:").setScore(4);
        objective.getScore(ChatColor.YELLOW + "Kills: 0").setScore(3);
        // Set the player's scoreboard
        player.setScoreboard(scoreboard);
    }
    private void updateScoreboard() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            createScoreboard(player);
        }


        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = player.getScoreboard();
            Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

            // Update the total player count on the scoreboard
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            objective.getScore(ChatColor.GRAY + "Players: " + onlinePlayers).setScore(5);

        }
    }

}
