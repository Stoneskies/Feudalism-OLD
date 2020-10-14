package com.stoneskies.feudalism.Commands.TownWar;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.confirmations.Confirmation;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.FeudalismMain;
import com.stoneskies.feudalism.Methods.RuinAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import com.stoneskies.feudalism.events.TownWar.TownWarDeclareEvent;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class DebugTownWarCommands {

    public static void exec(CommandSender sender, String[] args) throws NotRegisteredException {
        switch (args[0]) {
            case "declare":
                if (args.length >= 2) {
                    Resident resident = TownyAPI.getInstance().getDataSource().getResident(sender.getName());
                    if (resident.isKing()) {
                        Town town = TownyAPI.getInstance().getDataSource().getTown(args[1]);
                        if (!RuinAPI.isRuined(town)) {
                            if (!town.hasNation()) {
                                if (resident.getTown() == town) {
                                    Confirmation.runOnAccept(() -> {
                                        TownWarDeclareEvent customevent;
                                        try {
                                            customevent = new TownWarDeclareEvent(resident.getTown().getNation(), town);
                                            FeudalismMain.plugin.getServer().getPluginManager().callEvent(customevent);
                                        } catch (IOException | NotRegisteredException e) {
                                            e.printStackTrace();
                                        }
                                    }).setTitle("§bAre you sure you want to (debug) declare war on " + town.getName()).sendTo(sender);
                                } else {
                                    sender.sendMessage(ChatInfo.msg("&cYou can't declare war on yourself."));
                                }
                            } else {
                                sender.sendMessage(ChatInfo.msg("&cYou can't declare a town war against a town in a nation."));
                            }
                        } else {
                            sender.sendMessage(ChatInfo.msg("&cYou aren't a king!"));
                        }
                    } else {
                        sender.sendMessage(ChatInfo.msg("&cNot enough arguments!"));
                    }
                }
        }
    }
}
