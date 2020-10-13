package com.stoneskies.feudalism.Util;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.utils.NameUtil;

import java.util.ArrayList;
import java.util.List;

public class TownyTabCompleters {

    public static List<String> getTownyStartingWith(String arg, String type) {

        List<String> matches = new ArrayList<>();
        TownyUniverse townyUniverse = TownyUniverse.getInstance();

        if (type.contains("r")) {
            matches.addAll(townyUniverse.getResidentsTrie().getStringsFromKey(arg));
        }

        if (type.contains("t")) {
            matches.addAll(townyUniverse.getTownsTrie().getStringsFromKey(arg));
        }

        if (type.contains("n")) {
            matches.addAll(townyUniverse.getNationsTrie().getStringsFromKey(arg));
        }

        if (type.contains("w")) { // There aren't many worlds so check even if arg is empty
            matches.addAll(NameUtil.filterByStart(NameUtil.getNames(townyUniverse.getWorldMap().values()), arg));
        }

        return matches;
    }
}
