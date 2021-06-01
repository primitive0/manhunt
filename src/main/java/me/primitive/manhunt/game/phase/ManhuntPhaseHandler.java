package me.primitive.manhunt.game.phase;

import lombok.val;
import me.primitive.manhunt.Manhunt;
import me.primitive.manhunt.game.ManhuntGame;
import me.primitive.manhunt.game.container.ManhuntHunter;
import me.primitive.manhunt.game.container.ManhuntSpeedrunner;
import me.primitive.manhunt.game.phase.data.ManhuntSharedData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class ManhuntPhaseHandler extends AbstractPhaseHandler<Void> implements PhaseController<ManhuntSharedData> {

    private final ManhuntSharedData manhuntSharedData = new ManhuntSharedData();

    private AbstractPhaseHandler<ManhuntSharedData> phaseHandler = null;

    public ManhuntPhaseHandler(ManhuntGame game) {
        super(game, game);
    }

    @Override
    public void start() {
        val speedrunners = game.getTeamManager().getSpeedrunners();
        manhuntSharedData.initialize(speedrunners);

        phaseHandler = new CountdownPhaseHandler(game, this);
        phaseHandler.start();
    }

    @Override
    public void stop() {
        phaseHandler.stop();
    }

    @Override
    public void onSpeedrunnerJoins(ManhuntSpeedrunner speedrunner) {
        phaseHandler.onSpeedrunnerJoins(speedrunner);
    }

    @Override
    public void onHunterJoins(ManhuntHunter hunter) {
        phaseHandler.onHunterJoins(hunter);
    }

    @Override
    public void onSpeedrunnerDies(ManhuntSpeedrunner speedrunner) {
        val aliveSpeedrunners = manhuntSharedData.getAliveSpeedrunners();

        aliveSpeedrunners.remove(speedrunner);
        SpeedrunnerDeathHandler.schedule(speedrunner);

        if (aliveSpeedrunners.isEmpty()) {
            onHuntersWin();
        }
    }

    @Override
    public void onHunterDies(ManhuntHunter hunter) {
        phaseHandler.onHunterDies(hunter);
    }

    @Override
    public void onEnderDragonDies() {
        onSpeedrunnersWin();
    }

    private void onSpeedrunnersWin() {
        Bukkit.getOnlinePlayers()
            .forEach(player -> player.sendMessage("speedrunners won!"));

        endPhase();
    }

    private void onHuntersWin() {
        Bukkit.getOnlinePlayers()
            .forEach(player -> player.sendMessage("hunters won!"));

        endPhase();
    }

    private void endPhase() {
        phaseHandler.stop();
        game.notifyPhaseEnded(NextPhase.END);
    }

    @Override
    public void notifyPhaseEnded(@NotNull NextPhase nextPhase) {
        if (nextPhase == NextPhase.HUNT) {
            phaseHandler = new HuntPhaseHandler(game, this);
        } else {
            throw new IllegalStateException("Unexpected phase");
        }
    }

    @Override
    public ManhuntSharedData getSharedData() {
        return manhuntSharedData;
    }

    private static class SpeedrunnerDeathHandler extends BukkitRunnable {

        private final ManhuntSpeedrunner speedrunner;
        private final Location deathLocation;

        private SpeedrunnerDeathHandler(ManhuntSpeedrunner speedrunner) {
            this.speedrunner = speedrunner;

            val player = speedrunner.getPlayer();
            deathLocation = player.getLocation();
            player.setGameMode(GameMode.SPECTATOR);

            this.runTask(Manhunt.INSTANCE);
        }

        @Override
        public void run() {
            if (speedrunner.isOnline()) {
                val player = speedrunner.getPlayer();
                player.spigot().respawn();
                player.teleport(deathLocation);
            }
        }

        public static void schedule(ManhuntSpeedrunner speedrunner) {
            new SpeedrunnerDeathHandler(speedrunner);
        }
    }
}
