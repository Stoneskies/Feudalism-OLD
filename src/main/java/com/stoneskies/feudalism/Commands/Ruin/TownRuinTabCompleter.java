package com.stoneskies.feudalism.Commands.Ruin;

import com.stoneskies.feudalism.Util.TownyTabCompleters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class TownRuinTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> result = null;
        if(args.length == 4) {
            result = TownyTabCompleters.getTownyStartingWith(args[3], "t");
        }
        return result;
    }
}
