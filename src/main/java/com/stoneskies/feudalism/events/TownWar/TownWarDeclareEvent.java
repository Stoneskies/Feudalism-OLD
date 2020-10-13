package com.stoneskies.feudalism.events.TownWar;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.io.IOException;

public class TownWarDeclareEvent extends Event {
    public static Nation attacker;
    public static Town defender;
    public static long time = System.currentTimeMillis();

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public TownWarDeclareEvent(Nation nation, Town town) throws IOException {
        Bukkit.broadcastMessage(ChatInfo.msg("&b" + nation.getName() + " has declared war on the town of " + town.getName()));
        String filename = nation.getName() + town.getName() + ".yml";
        File warfile = new File("plugins/Feudalism/database/townwars", filename);
        YamlConfiguration wardata = new YamlConfiguration();
        if(!warfile.exists()) {
            warfile.getParentFile().mkdir();
            warfile.createNewFile();
            try {
                wardata.load(warfile);
                wardata.set("attacker", nation.getName());
                wardata.set("defender", town.getName());
                wardata.set("warscore", 0);
                wardata.set("time-started", time);
                wardata.set("nationtownsoccupied", null);
                wardata.set("nationcapitaloccupied", false);
                wardata.set("townoccupied", false);
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public static long getTime() {
        return time;
    }

    public static Nation getAttacker() {
        return attacker;
    }

    public static Town getDefender() {
        return defender;
    }
}
