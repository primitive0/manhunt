package me.primitive.manhunt.listeners;

import me.primitive.manhunt.map.util.CompassMapRenderer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CompassMoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (CompassMapRenderer.trackedPlayer == null)
            return;


        for (Player player : CompassMapRenderer.hunterRotateMap.keySet()) {
            Location hunterPos = player.getLocation();
            Location targetPos = CompassMapRenderer.trackedPlayer.getLocation();

            double rotate = Math.atan((hunterPos.getX() - targetPos.getX()) / (hunterPos.getZ() - targetPos.getZ())) + Math.toRadians(hunterPos.getYaw());
            if (hunterPos.getZ() > targetPos.getZ()) rotate -= Math.PI;

            if (rotate > Math.PI * 2) rotate = rotate % (Math.PI * 2);
            if (rotate < 0) rotate += Math.PI * 2;

            CompassMapRenderer.hunterRotateMap.replace(player, rotate);
        }
    }
}
