package me.primitive.manhunt;

import lombok.val;
import me.primitive.manhunt.containers.ManhuntHunter;
import me.primitive.manhunt.containers.ManhuntPlayer;
import me.primitive.manhunt.util.ManhuntUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public final class ManhuntGame {
    private ManhuntGame() {
    }

    enum Team {
        PLAYERS,
        HUNTERS,
    }

    enum StartState {
        STARTED_SUCCESSFULLY,
        NOT_ENOUGH_PLAYERS,
        ALREADY_STARTED,
    }

    private static final HashMap<OfflinePlayer, Team> playerTeamMap = new HashMap<>();

    private static int countdownValue;
    private static BukkitTask counter;

    private static List<ManhuntPlayer> alivePlayers;
    private static ManhuntHunter[] aliveHunters;

    public static void joinPlayers(Player player) {
        val offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());

        playerTeamMap.put(offlinePlayer, Team.PLAYERS);
    }

    public static void joinHunters(Player player) {
        val offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());

        playerTeamMap.put(offlinePlayer, Team.HUNTERS);
    }

    public static void leaveTeam(Player player) {
        playerTeamMap.remove(player);
    }

    public static boolean setCountdown(int value) {
        if (value < 0)
            return false;

        countdownValue = value;

        return true;
    }

    public static boolean startGame() {
        if (isGameRunning())
            return false;

        alivePlayers = new ArrayList<>();
        val huntersList = new ArrayList<ManhuntHunter>();

        for (val i : playerTeamMap.entrySet()) { //TODO: FIX
            val offlinePlayer = i.getKey();
            if (offlinePlayer.isOnline()) {
                val player = offlinePlayer.getPlayer();

                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().clear();

                if (i.getValue() == Team.PLAYERS) {
                    alivePlayers.add(new ManhuntPlayer(offlinePlayer));
                } else {
                    huntersList.add(new ManhuntHunter(offlinePlayer));
                }
            }
        }

        aliveHunters = huntersList.toArray(new ManhuntHunter[0]);

        if (countdownValue != 0) {
            freezeHuntersUnchecked();
            runCountdown();
        }

        for (val hunter : aliveHunters) {
            ManhuntTrackerManager.giveTracker(hunter);
            //ManhuntTrackerManager.changeTrackerTarget(hunter, alivePlayers.get(0)); //TODO: optimize
        }

        return true;
    }

    public static boolean stopGame() {
        if (!isGameRunning())
            return false;

        stopUnchecked();

        return true;
    }

    private static void stopUnchecked() {
        if (counter != null) {
            counter.cancel();
            counter = null;
        }

        alivePlayers = null;
        aliveHunters = null;
    }

    public static boolean isGameRunning() {
        return alivePlayers != null;
    }

    public static List<ManhuntPlayer> getAlivePlayersUnchecked() {
        return alivePlayers;
    }

    /*public static List<ManhuntPlayer> getAlivePlayers() {
        return alivePlayers == null ? null : Collections.unmodifiableList(alivePlayers);
    }*/

    public static ManhuntHunter[] getAliveHunters() {
        return aliveHunters;
    }

    public static ManhuntHunter getAliveHunterByUUID(UUID uuid) {
        return Arrays.stream(aliveHunters)
                .filter(hunter -> hunter.getOfflinePlayer().getUniqueId().equals(uuid))
                .findAny()
                .orElse(null);
    }

    private static void runCountdown() {
        counter = new BukkitRunnable() {
            int countdown = countdownValue;

            @Override
            public void run() { //TODO: sync
                String text;

                if (countdown == 0) {
                    text = "go!";
                    this.cancel();
                    counter = null;
                } else {
                    text = Integer.toString(countdown);
                }

                val playSound = countdown <= 5;

                Arrays.stream(aliveHunters)
                        .map(ManhuntHunter::getOfflinePlayer)
                        .filter(OfflinePlayer::isOnline)
                        .forEach(offlinePlayer -> {
                            val player = offlinePlayer.getPlayer();

                            player.spigot()
                                    .sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));

                            if (playSound)
                                ManhuntUtil.playTickSound(player);
                        });

                countdown--;
            }
        }.runTaskTimer(ManhuntPlugin.INSTANCE, 0L, 20L);
    }

    //before calling this function check out that all hunters are online
    private static void freezeHuntersUnchecked() {
        val ticksCountdown = countdownValue * 20;

        for (val hunter : aliveHunters) {
            val player = hunter.getOfflinePlayer().getPlayer();

            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.SLOW, ticksCountdown, 255)
            );

            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.JUMP, ticksCountdown, 128)
            );

            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.BLINDNESS, ticksCountdown, 255)
            );
        }
    }

    public static void onPlayerDies(ManhuntPlayer manhuntPlayer) {
        alivePlayers.remove(manhuntPlayer);

        val player = manhuntPlayer.getOfflinePlayer().getPlayer();
        val deathLocation = player.getLocation();

        new BukkitRunnable() { //let server handle death event properly
            @Override
            public void run() {
                player.setGameMode(GameMode.SPECTATOR);
                player.spigot().respawn();
                player.teleport(deathLocation);
            }
        }.runTask(ManhuntPlugin.INSTANCE);

        if (alivePlayers.isEmpty())
            onHuntersWin();
    }

    public static void onHunterDies(ManhuntHunter hunter) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (hunter.isOnline()) {
                    val player = hunter.getPlayer();
                    player.spigot().respawn();
                    ManhuntTrackerManager.giveTracker(hunter);
                }
            }
        }.runTask(ManhuntPlugin.INSTANCE);
        //TODO
    }

    public static void onPlayersWin() {
        stopUnchecked();

        Bukkit.getOnlinePlayers().forEach(player -> {
            ManhuntUtil.showWinTitle(player, "Players win!");
            ManhuntUtil.playPlayersWinSound(player);
        });
    }

    public static void onHuntersWin() {
        stopUnchecked();

        Bukkit.getOnlinePlayers().forEach(player -> {
            ManhuntUtil.showWinTitle(player, "Hunters win!");
            ManhuntUtil.playHuntersWinSound(player);
        });
    }
}
