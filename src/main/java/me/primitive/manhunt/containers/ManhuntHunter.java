package me.primitive.manhunt.containers;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public final class ManhuntHunter extends ManhuntPlayerContainer {
    public ManhuntHunter(OfflinePlayer player) {
        super(player);
    }

    public ManhuntPlayer trackedPlayer;

    public Location lastLocation;
}
