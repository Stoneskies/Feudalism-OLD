package com.stoneskies.feudalism.Commands.TownWar;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Methods.TownWarAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class TownWarCommands {
    private static YamlConfiguration wardata = new YamlConfiguration();

    public static void exec(CommandSender sender, String[] args) throws IOException, InvalidConfigurationException {
        try {
            Resident resident = TownyAPI.getInstance().getDataSource().getResident(sender.getName());
            if (resident.hasTown()) {
                if (!resident.getTown().hasNation()) {
                    if (TownWarAPI.isTownAtWar(resident.getTown().getUuid())) {
                        File file = TownWarAPI.getWarData(resident.getTown().getUuid());
                        if (file != null) {
                            Town town = TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown();
                            Nation nation = TownyUniverse.getInstance().getDataSource().getNation(args[1]);
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy");
                            wardata.load(file);
                            sender.sendMessage("§6.oOo._________________.[ §e" + nation.getName() + " §6vs§e " + town.getName() + "§6 ]._________________.oOo.");
                            sender.sendMessage("§2Warscore: §f" + wardata.getString("warscore"));
                            sender.sendMessage("§2Started: §f" + sdf.format(wardata.getLong("time-started")));
                            sender.sendMessage("§2Aggressor: §f" + nation.getFormattedName());
                            sender.sendMessage("§2Defender: §f" + town.getFormattedName());
                        }
                    } else {sender.sendMessage(ChatInfo.msg("&cYou aren't at war"));}
                }
                if (resident.getTown().hasNation()) {
                    if (TownWarAPI.isNationAtWar(resident.getTown().getNation().getUuid())) {
                        File file = TownWarAPI.getWarData(resident.getTown().getNation().getUuid());
                        if (file != null) {
                            YamlConfiguration wardata = new YamlConfiguration();
                            Town town = TownyUniverse.getInstance().getDataSource().getTown(args[1]);
                            Nation nation = resident.getTown().getNation();
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy");
                            wardata.load(file);
                            sender.sendMessage("§6.oOo._________________.[ §e" + nation.getName() + " §6vs§e " + town.getName() + "§6 ]._________________.oOo.");
                            sender.sendMessage("§2Warscore: §f" + wardata.getString("warscore"));
                            sender.sendMessage("§2Started: §f" + sdf.format(wardata.getLong("time-started")));
                            sender.sendMessage("§2Aggressor: §f" + nation.getFormattedName());
                            sender.sendMessage("§2Defender: §f" + town.getFormattedName());
                        }
                    } else {sender.sendMessage(ChatInfo.msg("&cYou aren't at war"));}
                }
            } else {sender.sendMessage(ChatInfo.msg("&cYou don't have a town."));}
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }
    }
}
