package com.stoneskies.feudalism.Commands;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.stoneskies.feudalism.Commands.Ruin.DebugRuinCommands;
import com.stoneskies.feudalism.Commands.TownWar.DebugTownWarCommands;
import com.stoneskies.feudalism.Methods.RuinAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class DebugCommands {
    public static void exec(CommandSender sender, String[] args) throws NotRegisteredException {
        if (sender.hasPermission("feudalism.debug")) {
            // args here: /fd debug args[1] args[2] args[3]...
            if (!(args.length >= 2)) {
                // not enough arguments, return is required here!
                sender.sendMessage(ChatInfo.msg("&cSpecify debug command!"));
                return;
            }
            String[] newargs = Arrays.copyOfRange(args, 2, args.length); // args[] without the first 2 elements
            switch (args[1]) {
                case "townruin":
                    // set the executor to be the debug ruin commands
                    DebugRuinCommands.exec(sender, newargs);
                    break;

                case "npcclear":
                    RuinAPI.clearresidentNPCs();
                    sender.sendMessage(ChatInfo.msg("Task Executed."));
                    break;
                case "townwar":
                    DebugTownWarCommands.exec(sender, newargs);
                    break;
                default:
                    // debug command invalid
                    sender.sendMessage(ChatInfo.msg("&c" + args[1] + " is not registered"));
                    break;
            }
        } else {sender.sendMessage(ChatInfo.msg("&cYou don't have permission to do that."));}
    }
}
