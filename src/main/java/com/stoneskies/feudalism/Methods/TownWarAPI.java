package com.stoneskies.feudalism.Methods;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TownWarAPI {
    private static final Path datafolder = Paths.get("plugins/Feudalism/database/townwars");
     private static final File[] wars = datafolder.toFile().listFiles();
    private static final YamlConfiguration wardata = new YamlConfiguration();


    public static boolean isAtWar(Nation nation, Town town) {
        // name of the file, townnaton.yml
        String warstring = town.getName() + nation.getName() + ".yml";
        // file of the inputted town
        File warfile = new File("plugins/Feudalism/database/townwars", warstring);
        boolean result = false;
        // if the file exists
        if (warfile.exists()) {
            result = true;
        }
        return result;
    }

    public static boolean isTownAtWar(String townname) throws IOException, InvalidConfigurationException {
        boolean result = false;
        if (wars != null) {
            for (File warfile : wars) {
                wardata.load(warfile);
                result = wardata.getString("defender").equals(townname);
            }
        }
        return result;
    }

    public static boolean isNationAtWar(String nationname) throws IOException, InvalidConfigurationException {
        boolean result = false;
        if (wars != null) {
            for (File warfile : wars) {
                wardata.load(warfile);
                result = wardata.getString("attacker").equals(nationname);
            }
        }
        return result;
    }

    public static File getWarData(String name) throws IOException, InvalidConfigurationException {
        File file = null;
        if (wars != null) {
            for (File warfile : wars) {
                wardata.load(warfile);
                if (wardata.getString("attacker").equals(name) || wardata.getString("defender").equals(name)) {
                    file = warfile;
                }
            }
        }
        return file;
    }

}
