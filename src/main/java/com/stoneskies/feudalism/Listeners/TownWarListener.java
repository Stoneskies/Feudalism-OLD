package com.stoneskies.feudalism.Listeners;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Methods.TownWarAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TownWarListener implements Listener {

    @EventHandler
    public static void onTownEnter(PlayerChangePlotEvent event) throws NotRegisteredException {
        if (TownyUniverse.getInstance().getDataSource().getResident(event.getPlayer().getName()).hasTown() && event.getTo().hasTownBlock() && !event.getFrom().hasTownBlock()) {
            Town town = TownyUniverse.getInstance().getDataSource().getResident(event.getPlayer().getName()).getTown();
            Resident resident = TownyUniverse.getInstance().getDataSource().getResident(event.getPlayer().getName());
            if (town.hasNation()) {
                if (TownWarAPI.isAtWar(town.getNation(), resident.getTown())) {event.getPlayer().sendMessage(ChatInfo.msg("&cYour town is currently at war with this nation."));}
            }
            if (resident.hasNation()) {
                if (TownWarAPI.isAtWar(town.getNation(), resident.getTown())) {event.getPlayer().sendMessage(ChatInfo.msg("&cYour Nation is currently at war with this town."));}
            }
        }
    }

    @EventHandler
    public static void onKillEvent(PlayerDeathEvent event) throws NotRegisteredException {
        if(event.getEntity().getKiller() instanceof Player && TownyUniverse.getInstance().getDataSource().getResident(event.getEntity().getName()).hasTown()) {

        }
    }

}
