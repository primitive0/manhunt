package me.primitive.manhunt.containers;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public final class ManhuntHunter extends ManhuntPlayerContainer {

    public ManhuntPlayer trackedPlayer;
    public Location lastLocation;

    public ManhuntHunter(OfflinePlayer player) {
        super(player);
    }
}
