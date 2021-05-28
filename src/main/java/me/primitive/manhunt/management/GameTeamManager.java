package me.primitive.manhunt.management;

import java.util.ArrayList;
import java.util.Collection;
import me.primitive.manhunt.containers.PlayerInPvh;
import me.primitive.manhunt.containers.PvhHunter;
import me.primitive.manhunt.containers.PvhPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GameTeamManager {

    public static final GameTeamManager INSTANCE = new GameTeamManager();
    public final Scoreboard pvhScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    public final Team playersTeam;
    public final Team huntersTeam;
    public final Collection<PlayerInPvh> pvhPlayers = new ArrayList<>(); //TODO: fix for offline players and synchronization
    private boolean locked = false;

    private GameTeamManager() {
        playersTeam = pvhScoreboard.registerNewTeam("players");
        huntersTeam = pvhScoreboard.registerNewTeam("hunters");
    }

    public void addPvhPlayer(Player player) {
        if (locked) {
            throw new IllegalStateException();
        }

        if (whoIsPlayer(player) != RegisteredAs.NONE) {
            throw new IllegalArgumentException();
        }

        player.setScoreboard(pvhScoreboard);
        playersTeam.addEntry(player.getName());
        pvhPlayers.add(new PvhPlayer(player)); //TODO: Maybe should be fixed
    }

    public void addPvhHunter(Player player) throws IllegalArgumentException {
        if (locked) {
            throw new IllegalStateException();
        }

        if (whoIsPlayer(player) != RegisteredAs.NONE) {
            throw new IllegalArgumentException();
        }

        player.setScoreboard(pvhScoreboard);
        huntersTeam.addEntry(player.getName());
        pvhPlayers.add(new PvhHunter(player)); //TODO: Maybe should be fixed
    }

    public void removePlayer(Player player) {
        if (locked) {
            throw new IllegalStateException();
        }

        for (PlayerInPvh playerInPvh : pvhPlayers) {
            if (player == playerInPvh.getBukkitPlayer()) {
                switch (whoIsPlayer(player)) {
                    case PLAYER:
                        playersTeam.removeEntry(player.getName());
                        break;

                    case HUNTER:
                        huntersTeam.removeEntry(player.getName());
                        break;
                }

                pvhPlayers.remove(playerInPvh);

                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

                return;
            }
        }

        throw new IllegalStateException();
    }

    public RegisteredAs whoIsPlayer(Player player) {
        return pvhPlayers.stream()
            .filter(playerInPvh -> player == playerInPvh.getBukkitPlayer())
            .findFirst()
            .map(playerInPvh -> playerInPvh instanceof PvhPlayer ? RegisteredAs.PLAYER : RegisteredAs.HUNTER)
            .orElse(RegisteredAs.NONE);
    }

    public void setLock(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public enum RegisteredAs {
        NONE,
        PLAYER,
        HUNTER
    }
}