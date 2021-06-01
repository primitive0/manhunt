package me.primitive.manhunt.game.phase;

import org.jetbrains.annotations.NotNull;

public interface PhaseController<T> {

    void notifyPhaseEnded(final @NotNull NextPhase nextPhase);

    T getSharedData();
}
