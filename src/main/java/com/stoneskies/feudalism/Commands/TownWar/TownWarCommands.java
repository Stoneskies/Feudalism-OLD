package com.stoneskies.feudalism.Commands.TownWar;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
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

    public static void exec(CommandSender sender, String[] args) {
        try {
            if (TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).hasTown()) {
                if (!TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().hasNation() && TownWarAPI.isAtWar(args[1], TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getName())) {
                    File file = TownWarAPI.getWarData(TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getName());
                    YamlConfiguration wardata = new YamlConfiguration();
                    Town town = TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown();
                    Nation nation = TownyUniverse.getInstance().getDataSource().getNation(args[1]);
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                    wardata.load(file);
                    sender.sendMessage("§6.oOo._________________.[ " + nation.getFormattedName() + " vs " + town.getFormattedName() + " ]._________________.oOo.");
                    sender.sendMessage("§2Warscore: §f" + wardata.getString("warscore"));
                    sender.sendMessage("§2Started: §f" + sdf.format(wardata.getLong("time-started")));
                    sender.sendMessage("§2Aggressor: §f" + nation.getFormattedName());
                    sender.sendMessage("§2Defender: §f" + town.getFormattedName());
                } else {
                    if (TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().hasNation() && TownWarAPI.isAtWar(TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getNation().getName(), args[1])) {
                        File file = TownWarAPI.getWarData(TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getName());
                        YamlConfiguration wardata = new YamlConfiguration();
                        Town town = TownyUniverse.getInstance().getDataSource().getTown(args[1]);
                        Nation nation = TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getNation();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                        wardata.load(file);
                        sender.sendMessage("§6.oOo._________________.[ " + nation.getFormattedName() + " vs " + town.getFormattedName() + " ]._________________.oOo.");
                        sender.sendMessage("§2Warscore: §f" + wardata.getString("warscore"));
                        sender.sendMessage("§2Started: §f" + sdf.format(wardata.getLong("time-started")));
                        sender.sendMessage("§2Aggressor: §f" + nation.getFormattedName());
                        sender.sendMessage("§2Defender: §f" + town.getFormattedName());
                    }
                }
            }
        } catch (NotRegisteredException | IOException | InvalidConfigurationException e) {
            sender.sendMessage(ChatInfo.msg("&c" + args[1] + " is not registered"));
            e.printStackTrace();
        }
    }
}
