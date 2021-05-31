package me.primitive.manhunt.game;

import lombok.Getter;
import me.primitive.manhunt.game.container.ManhuntHunter;
import me.primitive.manhunt.game.container.ManhuntSpeedrunner;
import me.primitive.manhunt.game.phase.ManhuntPhaseHandler;
import me.primitive.manhunt.game.phase.PhaseController;
import me.primitive.manhunt.game.phase.PhaseType;
import org.jetbrains.annotations.NotNull;

public final class ManhuntGame implements PhaseController<Void> {

    private static ManhuntGame MANHUNT_GAME;

    public static ManhuntGame getInstance() {
        return MANHUNT_GAME == null ? MANHUNT_GAME = new ManhuntGame() : MANHUNT_GAME;
    }

    @Getter
    private boolean running = false;

    @Getter
    private final TeamManager teamManager = new TeamManager();

    @Getter
    private final GameSettings settings = new GameSettings();

    private ManhuntPhaseHandler phaseHandler = null;

    public StartResult start() {
        if (running) {
            return StartResult.ALREADY_STARTED;
        }

        if (teamManager.getSpeedrunners().size() > 0 && teamManager.getHunters().size() > 0) {
            teamManager.lock();

            running = true;
            phaseHandler = new ManhuntPhaseHandler(this);
            phaseHandler.start();

            return StartResult.OK;
        } else {
            return StartResult.NOT_ENOUGH_PLAYERS;
        }
    }

    public void forceStop() {
        if (!running) {
            throw new IllegalStateException("Game is not running");
        }

        phaseHandler.stop();

        endGame();
    }

    private void endGame() {
        running = false;
        teamManager.unlock();
    }

    public void onSpeedrunnerDies(ManhuntSpeedrunner speedrunner) {
        if (running) {
            phaseHandler.onSpeedrunnerDies(speedrunner);
        }
    }

    public void onHunterDies(ManhuntHunter hunter) {
        if (running) {
            phaseHandler.onHunterDies(hunter);
        }
    }

    public void onSpeedrunnerJoins(ManhuntSpeedrunner speedrunner) {
        if (running) {
            phaseHandler.onSpeedrunnerJoins(speedrunner);
        }
    }

    public void onHunterJoins(ManhuntHunter hunter) {
        if (running) {
            phaseHandler.onHunterJoins(hunter);
        } else {
            if (hunter.isFrozen()) {
                hunter.unfreeze();
            }
        }
    }

    public void onEnderDragonDies() {
        if (running) {
            phaseHandler.onEnderDragonDies();
        }
    }

    @Override
    public void notifyPhaseEnded(final @NotNull PhaseType nextPhase) {
        if (nextPhase == PhaseType.END) {
            endGame();
        } else {
            throw new IllegalStateException("Unexpected phase");
        }
    }

    @Override
    public Void getSharedData() {
        return null;
    }

    public enum StartResult {
        NOT_ENOUGH_PLAYERS,
        OK,
        ALREADY_STARTED
    }
}
