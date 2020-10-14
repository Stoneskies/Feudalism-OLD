package com.stoneskies.feudalism.Methods;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;

import java.io.File;

public class TownWarAPI {

    public static boolean isAtWar(Nation nation, Town town) {
        // name of the file, nationtown.yml
        String warstring = town.getName() + ".yml";
        // file of the inputted town
        File warfile = new File("plugins/Feudalism/database/ruinedtowns", warstring);
        boolean result = false;
        // if the file exists
        if (warfile.exists()) {
            result = true;
        }
        return result;
    }

}
