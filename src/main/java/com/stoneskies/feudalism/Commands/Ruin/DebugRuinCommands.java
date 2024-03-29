package com.stoneskies.feudalism.Commands.Ruin;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Methods.RuinAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.command.CommandSender;

public class DebugRuinCommands {
    public static void exec(CommandSender sender, String[] args) {
        // args here: /fd debug townruin args[0] args[1] args[2]... since we passed in newargs which is args without the first 2 elements
        boolean result = false;
        if(args.length != 0) {
            switch(args[0]) {
                case "purge":
                    RuinAPI.PurgeRuinedTowns();
                    break;
                case "purgeexpired":
                    RuinAPI.PurgeExpiredRuinedTowns();
                    break;
                case "mayor":
                    if (args.length > 2) {
                        try {
                            // check mayor's town
                            Resident mayor = TownyUniverse.getInstance().getDataSource().getResident(args[1]);
                            Town town = mayor.getTown();
                            // return whatever the isRuined() value of said town is.
                            result = RuinAPI.isRuined(town);
                        } catch (NotRegisteredException e) {
                            sender.sendMessage(ChatInfo.msg("&cCan't find a mayor with the name " + args[1]));
                        }
                        // send it to the player
                        sender.sendMessage(String.valueOf(result));
                    } else {sender.sendMessage(ChatInfo.msg("&cNot Enough Arguments."));}
                    break;
                default:
                    sender.sendMessage(ChatInfo.msg("&c" + args[0] + " is not registered"));
                    break;
            }
        } else {
            sender.sendMessage(ChatInfo.msg("&cPlease specify an argument!"));
        }
    }
}
