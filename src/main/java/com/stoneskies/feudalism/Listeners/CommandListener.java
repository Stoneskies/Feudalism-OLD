package com.stoneskies.feudalism.Listeners;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public static void onCommandEvent(PlayerCommandPreprocessEvent event) throws NotRegisteredException {
        switch(event.getMessage()) {

            case "/town":
                event.setCancelled(true);
                Player player = event.getPlayer();
                if (TownyUniverse.getInstance().getDataSource().getResident(player.getName()).hasTown()) {
                    Resident resident = TownyUniverse.getInstance().getDataSource().getResident(player.getName());
                    Town town = resident.getTown();
                    List<String> residentlist = getResidents(town);

                    SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy");
                    player.sendMessage(".oOo.________________.[ " + town.getFormattedName() + " ].________________.oOo.");
                    player.sendMessage("Board: " + town.getBoard());
                    player.sendMessage("Founded: " + sdf.format(town.getRegistered()));
                    player.sendMessage("Town Size: " + town.getPurchasedBlocks() + " / " + town.getTotalBlocks());
                    player.sendMessage("Perm:");
                    player.sendMessage("Tax: " + town.getTaxes());
                    player.sendMessage("Mayor: " + town.getMayor().getName());
                    player.sendMessage("Residents: " + String.join(", ", residentlist));
                } else {player.sendMessage("you don't have a town");}
        }
    }

    public static List<String> getResidents(Town town) {
        List<String> stringList = new java.util.ArrayList<>(Collections.singletonList("nullcancel"));
        for (Resident resident : town.getResidents()) {
            stringList.add(resident.getFormattedName());
            stringList.remove(0);
        }
        return stringList;
    }
}
