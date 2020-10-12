package com.stoneskies.feudalism.events.Ruin;

import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RuinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private static Player player;
    private static Town town;

    public RuinEvent(Player player, Town town) {
        Bukkit.broadcastMessage(ChatInfo.msg("&7" + town.getName() + " has become a ruined town."));
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Town getTown() {
        return town;
    }
}
