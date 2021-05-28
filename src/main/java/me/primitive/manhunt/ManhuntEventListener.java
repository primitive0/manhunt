package me.primitive.manhunt;

import lombok.val;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public final class ManhuntEventListener implements Listener {

    @EventHandler
    public void onPlayerDies(PlayerDeathEvent event) {
        if (ManhuntGame.isGameRunning()) {
            val playerUUID = event.getEntity().getUniqueId();

            for (val manhuntPlayer : ManhuntGame.getAlivePlayersUnchecked()) {
                if (manhuntPlayer.getOfflinePlayer().getUniqueId().equals(playerUUID)) {
                    ManhuntGame.onPlayerDies(manhuntPlayer);
                    return;
                }
            }

            for (val manhuntHunter : ManhuntGame.getAliveHunters()) {
                if (manhuntHunter.getOfflinePlayer().getUniqueId().equals(playerUUID)) {
                    ManhuntGame.onHunterDies(manhuntHunter);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onEnderDragonChangePhase(EnderDragonChangePhaseEvent event) {
        if (ManhuntGame.isGameRunning() && event.getNewPhase() == EnderDragon.Phase.DYING) {
            ManhuntGame.onPlayersWin();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (ManhuntGame.isGameRunning()) {
            val uuid = event.getPlayer().getUniqueId();

            for (val player : ManhuntGame.getAlivePlayersUnchecked()) {
                if (player.getOfflinePlayer().getUniqueId().equals(uuid)) {
                    ManhuntTrackerManager.onManhuntPlayerMove(player, event.getTo());
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (ManhuntGame.isGameRunning()) {
            val uuid = event.getPlayer().getUniqueId();

            for (val player : ManhuntGame.getAlivePlayersUnchecked()) {
                if (player.getOfflinePlayer().getUniqueId().equals(uuid)) {
                    ManhuntTrackerManager.onManhuntPlayerPortal(player, event.getFrom());
                    return;
                }
            }
        }
    }
}
