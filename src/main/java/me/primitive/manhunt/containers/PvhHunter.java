package me.primitive.manhunt.containers;

import org.bukkit.entity.Player;

public class PvhHunter extends PlayerInPvh {

    private PvhPlayer trackedPlayer;

    public PvhHunter(Player bukkitPlayer) {
        super(bukkitPlayer);
    }

    public PvhPlayer getTrackedPlayer() {
        return trackedPlayer;
    }
}
