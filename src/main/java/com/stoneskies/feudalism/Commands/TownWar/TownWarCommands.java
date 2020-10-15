package com.stoneskies.feudalism.Commands.TownWar;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Methods.TownWarAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class TownWarCommands {

    public static void exec(CommandSender sender, String[] args) throws IOException, InvalidConfigurationException {
        try {
            if (TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).hasTown()) {
                try {
                    if (!TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().hasNation() && TownWarAPI.isAtWar(args[0], TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getName())) {
                        File file = null;
                        try {
                            file = TownWarAPI.getWarData(TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getName());
                        } catch (NotRegisteredException e) {
                            e.printStackTrace();
                        }
                        YamlConfiguration wardata = new YamlConfiguration();
                        Town town = TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown();
                        Nation nation = TownyUniverse.getInstance().getDataSource().getNation(args[0]);
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                        wardata.load(file);
                        sender.sendMessage("§6.oOo._________________.[ " + nation.getFormattedName() + " vs " + town.getFormattedName() + " ]._________________.oOo.");
                        sender.sendMessage("§2Warscore: §f" + wardata.getString("warscore"));
                        sender.sendMessage("§2Started: §f" + sdf.format(wardata.getLong("time-started")));
                        sender.sendMessage("§2Aggressor: §f" + nation.getFormattedName());
                        sender.sendMessage("§2Defender: §f" + town.getFormattedName());
                    } else {
                        try {
                            if (TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().hasNation() && TownWarAPI.isAtWar(TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getNation().getName(), args[0])) {
                                File file = TownWarAPI.getWarData(TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getName());
                                YamlConfiguration wardata = new YamlConfiguration();
                                Town town = TownyUniverse.getInstance().getDataSource().getTown(args[0]);
                                Nation nation = TownyUniverse.getInstance().getDataSource().getResident(sender.getName()).getTown().getNation();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                                wardata.load(file);
                                sender.sendMessage("§6.oOo._________________.[ " + nation.getFormattedName() + " vs " + town.getFormattedName() + " ]._________________.oOo.");
                                sender.sendMessage("§2Warscore: §f" + wardata.getString("warscore"));
                                sender.sendMessage("§2Started: §f" + sdf.format(wardata.getLong("time-started")));
                                sender.sendMessage("§2Aggressor: §f" + nation.getFormattedName());
                                sender.sendMessage("§2Defender: §f" + town.getFormattedName());
                            }
                        } catch (NotRegisteredException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
            }
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }
    }
}
