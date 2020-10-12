package com.stoneskies.feudalism.events.Ruin;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ReclaimEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private static Resident resident;
    private static Town town;

    public ReclaimEvent(Resident resident, Town town) throws NotRegisteredException {
        resident.getPlayer().sendMessage(ChatInfo.msg("&7Town reclaimed, lead it into a better era."));
        Bukkit.broadcastMessage(ChatInfo.msg("&7" + resident.getName() + " has reclaimed " + resident.getTown().getName()));
    }

    public static void setResident(Resident resident) {
        ReclaimEvent.resident = resident;
    }

    public static void setTown(Town town) {
        ReclaimEvent.town = town;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static Player getPlayer() {
        return resident.getPlayer();
    }

    public static Town getTown() {
        return town;
    }

    public static Resident getResident() {
        return resident;
    }
}
