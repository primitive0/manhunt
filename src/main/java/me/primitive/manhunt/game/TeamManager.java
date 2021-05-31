package me.primitive.manhunt.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import lombok.val;
import me.primitive.manhunt.game.container.ManhuntHunter;
import me.primitive.manhunt.game.container.ManhuntPlayer;
import me.primitive.manhunt.game.container.ManhuntSpeedrunner;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TeamManager {

    private final HashMap<UUID, ManhuntSpeedrunner> OFFLINE_PLAYER_SPEEDRUNNER_MAP = new HashMap<>();
    private final HashMap<UUID, ManhuntHunter> OFFLINE_PLAYER_HUNTER_MAP = new HashMap<>();
    private final Collection<ManhuntPlayer> PLAYERS = new ArrayList<>();

    private boolean locked = false;

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    @NotNull
    public TeamPutResult putSpeedrunner(@NotNull OfflinePlayer player) {
        checkLocked();

        val uuid = player.getUniqueId();

        if (OFFLINE_PLAYER_SPEEDRUNNER_MAP.containsKey(uuid)) {
            return TeamPutResult.ALREADY_IN;
        }

        val speedrunner = new ManhuntSpeedrunner(uuid);
        if (OFFLINE_PLAYER_HUNTER_MAP.remove(uuid) == null) {
            PLAYERS.add(speedrunner);
        }
        OFFLINE_PLAYER_SPEEDRUNNER_MAP.put(uuid, speedrunner);

        return TeamPutResult.OK_SPEEDRUNNER;
    }

    @NotNull
    public TeamPutResult putHunter(@NotNull OfflinePlayer player) {
        checkLocked();

        val uuid = player.getUniqueId();

        if (OFFLINE_PLAYER_HUNTER_MAP.containsKey(uuid)) {
            return TeamPutResult.ALREADY_IN;
        }

        val hunter = new ManhuntHunter(uuid);
        if (OFFLINE_PLAYER_SPEEDRUNNER_MAP.remove(uuid) == null) {
            PLAYERS.add(hunter);
        }
        OFFLINE_PLAYER_HUNTER_MAP.put(uuid, hunter);

        return TeamPutResult.OK_HUNTER;
    }

    @NotNull
    public TeamDropResult dropManhuntPlayer(@NotNull OfflinePlayer player) {
        checkLocked();

        val uuid = player.getUniqueId();
        ManhuntPlayer manhuntPlayer = OFFLINE_PLAYER_SPEEDRUNNER_MAP.remove(uuid);
        if (manhuntPlayer == null) {
            manhuntPlayer = OFFLINE_PLAYER_HUNTER_MAP.remove(uuid);
            if (manhuntPlayer == null) {
                return TeamDropResult.NO_TEAM;
            }
        }

        PLAYERS.remove(manhuntPlayer);
        return TeamDropResult.OK;
    }

    private void checkLocked() {
        if (locked) {
            throw new IllegalStateException("Team manager is locked");
        }
    }

    @NotNull
    public Collection<? extends ManhuntPlayer> getPlayers() {
        return Collections.unmodifiableCollection(PLAYERS);
    }

    @NotNull
    public Collection<ManhuntSpeedrunner> getSpeedrunners() {
        return Collections.unmodifiableCollection(OFFLINE_PLAYER_SPEEDRUNNER_MAP.values());
    }

    @NotNull
    public Collection<ManhuntHunter> getHunters() {
        return Collections.unmodifiableCollection(OFFLINE_PLAYER_HUNTER_MAP.values());
    }

    @Nullable
    public ManhuntSpeedrunner getSpeedrunner(@NotNull OfflinePlayer player) {
        return OFFLINE_PLAYER_SPEEDRUNNER_MAP.get(player.getUniqueId());
    }

    @Nullable
    public ManhuntHunter getHunter(@NotNull OfflinePlayer player) {
        return OFFLINE_PLAYER_HUNTER_MAP.get(player.getUniqueId());
    }

    @Nullable
    public ManhuntPlayer getManhuntPlayer(@NotNull OfflinePlayer player) {
        for (val manhuntPlayer : PLAYERS) {
            if (manhuntPlayer.getUniqueId().equals(player.getUniqueId())) {
                return manhuntPlayer;
            }
        }

        return null;
    }

    public enum TeamPutResult {
        OK_SPEEDRUNNER,
        OK_HUNTER,
        ALREADY_IN
    }

    public enum TeamDropResult {
        OK,
        NO_TEAM
    }
}
