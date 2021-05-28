package me.primitive.manhunt.util;

import lombok.val;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public final class ManhuntUtil {

    public static void playTickSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 2.0f, 2.0f);
    }

    public static void playPlayersWinSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 0.5f);
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 0.8f, 1.0f);
    }

    public static void playHuntersWinSound(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 0.6f, 1.3f);
        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.3f, 1.7f);
    }

    public static void showWinTitle(Player player, String text) {
        player.sendTitle(text, null, 7, 60, 0);
    }

    public static void setCompassLodestoneLocation(ItemStack item, Location location) {
        val meta = (CompassMeta) item.getItemMeta();
        meta.setLodestone(location);
        item.setItemMeta(meta);
    }
}
