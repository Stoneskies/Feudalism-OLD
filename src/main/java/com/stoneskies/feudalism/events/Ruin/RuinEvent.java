package com.stoneskies.feudalism.events.Ruin;

import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.io.IOException;

public class RuinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private static Player player;
    private static Town town;
    private static File ruinedtown;
    private static YamlConfiguration ruinedtowndata;
    private static long time = System.currentTimeMillis();

    public RuinEvent(Player player, Town town) {
        Bukkit.broadcastMessage(ChatInfo.msg("&7" + town.getName() + " has become a ruined town."));
        // name of the town file, town.yml
        String ruinedtownstring = town.getName() + ".yml";
        ruinedtown = new File("plugins/Feudalism/database/ruinedtowns", ruinedtownstring);
        ruinedtowndata = new YamlConfiguration();
        // if the file doesn't exist
        if (!ruinedtown.exists()) {
            try {
                // create it
                ruinedtown.getParentFile().mkdir();
                ruinedtown.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // load the file's data
        try {
            ruinedtowndata.load(ruinedtown);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        // set the variables
        ruinedtowndata.set("time-fallen", time);
        ruinedtowndata.set("name", town.getName());
        ruinedtowndata.set("mayor", town.getMayor().getName());
        try {
            // save the file
            ruinedtowndata.save(ruinedtown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Town getTown() {
        return town;
    }
}
