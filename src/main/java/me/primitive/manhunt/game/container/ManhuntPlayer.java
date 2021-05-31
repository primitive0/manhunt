package me.primitive.manhunt.game.container;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ManhuntPlayer {

    private final UUID uuid;

    public ManhuntPlayer(@NotNull OfflinePlayer offlinePlayer) {
        this(offlinePlayer.getUniqueId());
    }

    public ManhuntPlayer(@NotNull UUID uuid) {
        this.uuid = uuid;
    }

    @NotNull
    public final OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    @Nullable
    public final Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @NotNull
    public final UUID getUniqueId() {
        return uuid;
    }

    public final boolean isOnline() {
        return Bukkit.getOfflinePlayer(uuid).isOnline();
    }
}
