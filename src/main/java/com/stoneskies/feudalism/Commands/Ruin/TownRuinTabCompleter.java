package com.stoneskies.feudalism.Commands.Ruin;

import com.stoneskies.feudalism.Util.TownyTabCompleters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class TownRuinTabCompleter implements TabCompleter {
    // tab completer for ruin commands
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> result = null;
        // if typing town name
        if(args.length == 4) {
            // get towns
            result = TownyTabCompleters.getTownyStartingWith(args[3], "t");
        }
        // if typing resident name
        if (args.length == 3) {
            // get residents
            result = TownyTabCompleters.getTownyStartingWith(args[2], "r");
        }
        return result;
    }
}
