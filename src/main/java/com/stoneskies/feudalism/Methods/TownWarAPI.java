package com.stoneskies.feudalism.Methods;

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

    public static boolean isTownAtWar(UUID townuuid) throws IOException, InvalidConfigurationException {
        boolean result = false;
        if (wars != null) {
            for (File warfile : wars) {
                wardata.load(warfile);
                result = UUID.fromString(wardata.getString("defender")).equals(townuuid);
            }
        }
        return result;
    }

    public static boolean isNationAtWar(UUID nationuuid) throws IOException, InvalidConfigurationException {
        boolean result = false;
        if (wars != null) {
            for (File warfile : wars) {
                wardata.load(warfile);
                result = UUID.fromString(wardata.getString("attacker")).equals(nationuuid);
            }
        }
        return result;
    }

    public static File getWarData(String uuid) throws IOException, InvalidConfigurationException {
        File file = null;
        if (wars != null) {
            for (File warfile : wars) {
                wardata.load(warfile);
                if (wardata.getString("attacker").equals(uuid) || wardata.getString("defender").equals(uuid)) {
                    file = warfile;
                }
            }
        }
        return file;
    }

}
