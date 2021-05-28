package me.primitive.manhunt.containers;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ManhuntPlayerContainer {

    @Getter
    public final OfflinePlayer offlinePlayer;

    public ManhuntPlayerContainer(OfflinePlayer player) {
        this.offlinePlayer = player;
    }

    public boolean isOnline() {
        return offlinePlayer.isOnline();
    }

    public Player getPlayer() {
        return offlinePlayer.getPlayer();
    }
}





