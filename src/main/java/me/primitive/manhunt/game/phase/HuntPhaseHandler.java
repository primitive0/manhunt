package me.primitive.manhunt.game.phase;

import me.primitive.manhunt.game.ManhuntGame;
import me.primitive.manhunt.game.container.ManhuntHunter;
import me.primitive.manhunt.game.phase.data.ManhuntSharedData;

public final class HuntPhaseHandler extends AbstractPhaseHandler<ManhuntSharedData> {

    public HuntPhaseHandler(ManhuntGame game, PhaseController<ManhuntSharedData> phaseController) {
        super(game, phaseController);
    }

    @Override
    public void stop() {
    }

    @Override
    public void onHunterJoins(ManhuntHunter hunter) {
        if (hunter.isFrozen()) {
            hunter.unfreeze();
        }
    }
}
