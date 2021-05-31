package me.primitive.manhunt.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class SoundUtil {

    public static void playTickSound(@NotNull Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 0.7F, 2.0F);
    }

    public static void playStartSound(@NotNull Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.28F, 1.5F);
    }
}
