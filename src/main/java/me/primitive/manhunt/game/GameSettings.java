package me.primitive.manhunt.game;

import lombok.Data;

@Data
public final class GameSettings {

    int countdown = 30; //default value

    public void setCountdown(final int countdown) {
        if (countdown < 0) {
            throw new IllegalArgumentException("Countdown should be greater or equal zero");
        }

        this.countdown = countdown;
    }
}
