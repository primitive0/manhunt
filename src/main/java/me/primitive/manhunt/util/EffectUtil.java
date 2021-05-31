package me.primitive.manhunt.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class EffectUtil {

    private static final PotionEffect SLOWNESS_MAX_EFFECT = new PotionEffect(
        PotionEffectType.SLOW,
        Integer.MAX_VALUE,
        255, false, false);

    private static final PotionEffect BLIND_EFFECT = new PotionEffect(
        PotionEffectType.BLINDNESS,
        Integer.MAX_VALUE,
        1, false, false);

    private static final PotionEffect JUMP_DISABLE_EFFECT = new PotionEffect(
        PotionEffectType.JUMP,
        Integer.MAX_VALUE,
        128, false, false); //128 is a magic value that prevents jumping

    public static void slowAndBlindPlayer(Player player) {
        player.addPotionEffect(SLOWNESS_MAX_EFFECT);
        player.addPotionEffect(JUMP_DISABLE_EFFECT);
        player.addPotionEffect(BLIND_EFFECT);
    }

    public static void unslowAndUnblindPlayer(Player player) {
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.JUMP);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
    }
}
