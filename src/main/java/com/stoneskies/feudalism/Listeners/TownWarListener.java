package com.stoneskies.feudalism.Listeners;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.FeudalismMain;
import com.stoneskies.feudalism.Methods.TownWarAPI;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TownWarListener implements Listener {
    public static YamlConfiguration wardata = new YamlConfiguration();

    @EventHandler
    public static void onTownEnter(PlayerChangePlotEvent event) {

    }

    @EventHandler
    public static void onKillEvent(PlayerDeathEvent event) throws InvalidConfigurationException {
        try {
            if (event.getEntity().getKiller() != null && TownyAPI.getInstance().getDataSource().getResident(event.getEntity().getName()).hasTown()) {
                Town town = null;
                try {
                    town = TownyAPI.getInstance().getDataSource().getResident(event.getEntity().getName()).getTown();
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
                if (!town.hasNation() && TownWarAPI.isTownAtWar(town.getUuid())) {
                    File file = TownWarAPI.getWarData(town.getUuid());
                    wardata.load(file);
                    if (wardata.getInt("attackerrkillscore") < FeudalismMain.plugin.getConfig().getInt("townwar-maxkillscore")) {
                        wardata.set("attackerkillscore", wardata.getInt("attackerkillscore") + FeudalismMain.plugin.getConfig().getInt("townwar-maxkillscore") / TownyUniverse.getInstance().getDataSource().getTown(UUID.fromString(wardata.getString("defender"))).getNumResidents());
                        wardata.set("warscore", wardata.getInt("warscore") + FeudalismMain.plugin.getConfig().getInt("townwar-maxkillscore") / TownyUniverse.getInstance().getDataSource().getTown(UUID.fromString(wardata.getString("attacker"))).getNumResidents());
                        wardata.save(file);
                    }
                }
                if (town.hasNation() && TownWarAPI.isNationAtWar(town.getNation().getUuid())) {
                    File file = TownWarAPI.getWarData(town.getNation().getUuid());
                    wardata.load(file);
                    if (wardata.getInt("defenderkillscore") < FeudalismMain.plugin.getConfig().getInt("townwar-maxkillscore")) {
                        wardata.set("defenderkillscore", wardata.getInt("defenderkillscore") + FeudalismMain.plugin.getConfig().getInt("townwar-maxkillscore") / TownyUniverse.getInstance().getDataSource().getTown(UUID.fromString(wardata.getString("defender"))).getNumResidents());
                        wardata.set("warscore", wardata.getInt("warscore") - FeudalismMain.plugin.getConfig().getInt("townwar-maxkillscore") / TownyUniverse.getInstance().getDataSource().getTown(UUID.fromString(wardata.getString("defender"))).getNumResidents());
                        wardata.save(file);
                    }
                }
            }
        } catch (NotRegisteredException | IOException e) {
            e.printStackTrace();
        }
    }
}
