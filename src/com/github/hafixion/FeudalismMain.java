package com.github.hafixion;

import com.github.hafixion.Ruin.DebugRuinCommands;
import com.github.hafixion.Ruin.RuinAPI;
import com.github.hafixion.Ruin.TownRuin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeudalismMain extends JavaPlugin {

    private static FeudalismMain plugin;

    public static void setPlugin(FeudalismMain plugin) {
        FeudalismMain.plugin = plugin;
    }

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Loaded Successfully.");
        registerStuff();
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Unloaded Successfully.");
    }
    public void registerStuff() {
        //commands
        this.getCommand("townruin").setExecutor(new DebugRuinCommands());
        //schedules
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, RuinAPI.ExpiredRuinedTownPurge, 0L, 72000L);
        //events
        getServer().getPluginManager().registerEvents(new TownRuin(), this);
    }
}
