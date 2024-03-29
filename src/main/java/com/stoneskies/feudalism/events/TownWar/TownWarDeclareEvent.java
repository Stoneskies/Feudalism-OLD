package com.stoneskies.feudalism.events.TownWar;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.FeudalismMain;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
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
        if (FeudalismMain.plugin.getConfig().getBoolean("townwar-enabled")) {
            // if war is enabled broadcast and create new file
            Bukkit.broadcastMessage(ChatInfo.msg("&b" + nation.getName() + " has declared war on the town of " + town.getName()));
            String filename = town.getName() + nation.getName() + ".yml";
            File warfile = new File("plugins/Feudalism/database/townwars", filename);
            YamlConfiguration wardata = new YamlConfiguration();
            if (!warfile.exists()) {
                // set default values
                warfile.getParentFile().mkdir();
                warfile.createNewFile();
                wardata.set("attacker", nation.getUuid().toString());
                wardata.set("defender", town.getUuid().toString());
                wardata.set("warscore", 0);
                wardata.set("time-started", time);
                wardata.set("nationtownsoccupied", null);
                wardata.set("nationcapitaloccupied", false);
                wardata.set("townoccupied", false);
                wardata.set("attackerkillscore", 0);
                wardata.set("defenderkillscore", 0);
                wardata.save(warfile);
            }
            town.setAdminEnabledPVP(true);
            for (Town nationTown : nation.getTowns()) {
                nationTown.setAdminEnabledPVP(true);
            }
        }
    }
    // get the time at which the event happened
    public static long getTime() {
        return time;
    }
    // get the attacker
    public static Nation getAttacker() {
        return attacker;
    }
    // get the defender
    public static Town getDefender() {
        return defender;
    }
}
