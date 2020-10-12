package com.stoneskies.feudalism.Commands.Ruin;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.confirmations.Confirmation;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.FeudalismMain;
import com.stoneskies.feudalism.Interfaces.RuinAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import com.stoneskies.feudalism.events.Ruin.ReclaimEvent;
import org.bukkit.command.CommandSender;

public class RuinCommands {

    public static void exec(CommandSender sender, String[] args) {
        Resident resident = null;
        try {
            resident = TownyAPI.getInstance().getDataSource().getResident(sender.getName());
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }
        if (args.length >= 3) {
            switch (args[1]) {
                // reclaim command
                case "reclaim":
                        if (FeudalismMain.plugin.getConfig().getBoolean("enable-resident-reclaiming")) {
                            if (resident.hasTown()) {
                                try {
                                    // see if the resident's town is ruined
                                    if (RuinAPI.isRuined(resident.getTown())) {
                                        Resident finalResident = resident;
                                        Resident finalResident1 = resident;
                                        Confirmation.runOnAccept(() -> {
                                            // reclaim the town under the resident's name
                                            try {
                                                RuinAPI.reclaim(finalResident, finalResident1.getTown());
                                                ReclaimEvent customevent;
                                                try {
                                                    customevent = new ReclaimEvent(finalResident, finalResident1.getTown());
                                                    FeudalismMain.plugin.getServer().getPluginManager().callEvent(customevent);
                                                } catch (NotRegisteredException e) {
                                                    e.printStackTrace();
                                                }
                                            } catch (NotRegisteredException e) {
                                                e.printStackTrace();
                                            }
                                        }).sendTo(sender);
                                    } else {
                                        resident.getPlayer().sendMessage(ChatInfo.msg("&cYour town isn't ruined."));
                                    }
                                } catch (NotRegisteredException e) {
                                    e.printStackTrace();
                                    resident.getPlayer().sendMessage(ChatInfo.msg("&cYou aren't in a town."));
                                }
                            } else {
                                resident.getPlayer().sendMessage(ChatInfo.msg("&cYou aren't in a town."));
                            }
                        } else {
                        sender.sendMessage(ChatInfo.msg("&cResident reclamation is disabled."));
                    }
                case "assign":
                    if (args.length >= 3) {
                        if (args.length >= 4) {
                            if (FeudalismMain.plugin.getConfig().getBoolean("enable-nation-reclaiming")) {
                                if (sender.hasPermission(FeudalismMain.plugin.getConfig().getString("nation-reclaiming-permission"))) {
                                    try {
                                        Town town = TownyAPI.getInstance().getDataSource().getTown(args[3]);
                                        Resident newmayor = TownyAPI.getInstance().getDataSource().getResident(args[2]);
                                        Resident cmdsender = TownyAPI.getInstance().getDataSource().getResident(sender.getName());
                                        if (RuinAPI.isRuined(town)) {
                                            if (town.getNation() == cmdsender.getTown().getNation()) {
                                                if (!newmayor.isMayor()) {
                                                    if(newmayor.getPlayer().isOnline()) {
                                                        Confirmation.runOnAccept(() -> {
                                                            Confirmation.runOnAccept(() -> {
                                                                RuinAPI.reclaim(newmayor, town);
                                                                try {
                                                                    TownyMessaging.sendNationMessagePrefixed(town.getNation(), "§b" + newmayor.getName() + " has been assigned to " + town.getName());
                                                                } catch (NotRegisteredException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }).sendTo(newmayor.getPlayer());
                                                        }).sendTo(sender);
                                                    } else {sender.sendMessage(ChatInfo.msg("That player is currently offline."));}
                                                } else {
                                                    sender.sendMessage(ChatInfo.msg("That player is currently a mayor."));
                                                }
                                            } else {
                                                sender.sendMessage(ChatInfo.msg("That town is not in your nation"));
                                            }
                                        } else {
                                            sender.sendMessage(ChatInfo.msg("That town isn't ruined"));
                                        }
                                    } catch (NotRegisteredException e) {
                                        e.printStackTrace();
                                        sender.sendMessage(ChatInfo.msg("Resident or town does not exist."));
                                    }
                                }
                            } else {sender.sendMessage(ChatInfo.msg("Nation reclaiming is disabled."));}
                        } else {sender.sendMessage(ChatInfo.msg("Not enough arguments."));}

                    } else {sender.sendMessage(ChatInfo.msg("Not enough arguments."));}
            }
        } else {
            sender.sendMessage(ChatInfo.msg("&cPlease specify an argument!"));
        }
    }
}
