package me.primitive.manhunt.game.phase;

import lombok.RequiredArgsConstructor;
import me.primitive.manhunt.game.ManhuntGame;
import me.primitive.manhunt.game.container.ManhuntHunter;
import me.primitive.manhunt.game.container.ManhuntSpeedrunner;

@RequiredArgsConstructor
public abstract class AbstractPhaseHandler<T> {

    protected final ManhuntGame game;
    protected final PhaseController<T> phaseController;

    public void start() {
    }

    public abstract void stop();

    public void onSpeedrunnerDies(ManhuntSpeedrunner speedrunner) {
    }

    public void onHunterDies(ManhuntHunter hunter) {
    }

    public void onSpeedrunnerJoins(ManhuntSpeedrunner speedrunner) {
    }

    public void onHunterJoins(ManhuntHunter hunter) {
    }

    public void onEnderDragonDies() {
    }
}
