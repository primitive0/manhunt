package me.primitive.manhunt.game.phase.data;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import me.primitive.manhunt.game.container.ManhuntSpeedrunner;

@Getter
public final class ManhuntSharedData {

    private boolean initialized = false;

    private final Collection<ManhuntSpeedrunner> aliveSpeedrunners = new ArrayList<>();

    public void initialize(Collection<ManhuntSpeedrunner> aliveSpeedrunners) {
        if (initialized) {
            throw new RuntimeException("Cannot initialize twice");
        }

        this.aliveSpeedrunners.addAll(aliveSpeedrunners);
        initialized = true;
    }
}
