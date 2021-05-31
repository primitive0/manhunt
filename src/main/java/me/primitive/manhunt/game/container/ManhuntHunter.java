package me.primitive.manhunt.game.container;

import java.util.UUID;
import lombok.Getter;
import me.primitive.manhunt.util.EffectUtil;

public final class ManhuntHunter extends ManhuntPlayer {

    @Getter
    private boolean frozen = false;

    public ManhuntHunter(UUID uuid) {
        super(uuid);
    }

    public void freeze() {
        checkOnline();
        frozen = true;
        EffectUtil.slowAndBlindPlayer(getPlayer());
    }

    public void unfreeze() {
        checkOnline();
        frozen = false;
        EffectUtil.unslowAndUnblindPlayer(getPlayer());
    }

    private void checkOnline() {
        if (!isOnline()) {
            throw new IllegalStateException("Player is not online");
        }
    }
}
