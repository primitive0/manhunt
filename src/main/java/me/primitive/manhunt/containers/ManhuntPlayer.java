package me.primitive.manhunt.containers;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public final class ManhuntPlayer extends ManhuntPlayerContainer {
    public ManhuntPlayer(OfflinePlayer player) {
        super(player);
    }

    public Location lastLocation;
    public Location lastOverworldLocation;
}
