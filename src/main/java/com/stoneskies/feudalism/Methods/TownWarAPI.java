package com.stoneskies.feudalism.Methods;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class TownWarAPI {
    private static Path datafolder = Paths.get("plugins/Feudalism/database/townwars");
    private static File[] wars = datafolder.toFile().listFiles();
    private static YamlConfiguration wardata = new YamlConfiguration();

    // if town is that at war
    public static boolean isTownAtWar(UUID townuuid) throws IOException, InvalidConfigurationException {
        boolean result = false;
        // if any wars exist
        if (wars != null) {
            // load a war file
            for (File warfile : wars) {
                wardata.load(warfile);
                // if the uuids are equal
                result = wardata.getString("defender").equals(townuuid.toString());
            }
        }
        return result;
    }

    public static boolean isNationAtWar(UUID nationuuid) throws IOException, InvalidConfigurationException {
        // if nation is at war
        boolean result = false;
        // if there are any wars
        if (wars != null) {
            //load a war file
            for (File warfile : wars) {
                wardata.load(warfile);
                // check if uuids are the same
                result = wardata.getString("attacker").equals(nationuuid.toString());
            }
        }
        return result;
    }

    public static File getWarData(UUID uuid) throws IOException, InvalidConfigurationException {
        // for getting the war file of a war
        File file = null;
        // if any wars exist
        if (wars != null) {
            // load a war file
            for (File warfile : wars) {
                wardata.load(warfile);
                // if attack or defender = the uuid then return file
                if (wardata.getString("attacker").equals(uuid.toString()) || wardata.getString("defender").equals(uuid.toString())) {
                    file = warfile;
                }
            }
        }
        return file;
    }

    public static Nation getNationAtWarWith(Town town) {
        // get nation which town is at war with
        Nation nation = null;
        try {
            // load war data
            File file = getWarData(town.getUuid());
            wardata.load(file);
            // get nation
            UUID uuid = UUID.fromString(wardata.getString("attacker"));
            nation = TownyUniverse.getInstance().getDataSource().getNation(uuid);
        } catch (IOException | InvalidConfigurationException | NotRegisteredException e) {
            e.printStackTrace();
        }
        return nation;
    }
}
