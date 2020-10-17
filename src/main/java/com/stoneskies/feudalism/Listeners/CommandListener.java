package com.stoneskies.feudalism.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler
    public static void onCommandEvent(PlayerCommandPreprocessEvent event) {
        switch(event.getMessage()) {

            case "/town":
                event.getPlayer().sendMessage(event.getMessage());
                event.setCancelled(true);
        }
    }
}
