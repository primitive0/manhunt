package me.primitive.manhunt.listener;

import lombok.val;
import me.primitive.manhunt.game.ManhuntGame;
import me.primitive.manhunt.game.container.ManhuntHunter;
import me.primitive.manhunt.game.container.ManhuntSpeedrunner;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public final class ManhuntEventListener implements Listener {

    @EventHandler
    public void onPlayerDies(PlayerDeathEvent event) {
        val game = ManhuntGame.getInstance();

        val manhuntPlayer = game.getTeamManager().getManhuntPlayer(event.getEntity());
        if (manhuntPlayer instanceof ManhuntSpeedrunner) {
            game.onSpeedrunnerDies((ManhuntSpeedrunner) manhuntPlayer);
        } else if (manhuntPlayer instanceof ManhuntHunter) {
            game.onHunterDies((ManhuntHunter) manhuntPlayer);
        }
    }

    @EventHandler
    public void onPlayerJoins(PlayerJoinEvent event) {
        val game = ManhuntGame.getInstance();

        val manhuntPlayer = game.getTeamManager().getManhuntPlayer(event.getPlayer());
        if (manhuntPlayer instanceof ManhuntSpeedrunner) {
            game.onSpeedrunnerJoins((ManhuntSpeedrunner) manhuntPlayer);
        } else if (manhuntPlayer instanceof ManhuntHunter) {
            game.onHunterJoins((ManhuntHunter) manhuntPlayer);
        }
    }

    @EventHandler
    public void onEnderDragonChangePhase(EnderDragonChangePhaseEvent event) {
        if (event.getNewPhase() == EnderDragon.Phase.DYING) {
            ManhuntGame.getInstance().onEnderDragonDies();
        }
    }
}
