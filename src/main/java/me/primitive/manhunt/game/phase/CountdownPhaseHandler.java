package me.primitive.manhunt.game.phase;

import lombok.val;
import me.primitive.manhunt.Manhunt;
import me.primitive.manhunt.game.ManhuntGame;
import me.primitive.manhunt.game.container.ManhuntHunter;
import me.primitive.manhunt.game.phase.data.ManhuntSharedData;
import me.primitive.manhunt.util.SoundUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.scheduler.BukkitRunnable;

public final class CountdownPhaseHandler extends AbstractPhaseHandler<ManhuntSharedData> {

    private CountdownTask countdownTask;

    public CountdownPhaseHandler(ManhuntGame game, PhaseController<ManhuntSharedData> phaseController) {
        super(game, phaseController);
    }

    @Override
    public void start() {
        if (game.getSettings().getCountdown() == 0) {
            endPhase();
        } else {
            freezeHunters();
            countdownTask = new CountdownTask();
        }
    }

    @Override
    public void stop() {
        if (countdownTask == null) {
            throw new IllegalStateException("Unable to stop non-started phase handler");
        }

        countdownTask.cancel();
        unfreezeHunters();
    }

    @Override
    public void onHunterJoins(ManhuntHunter hunter) {
        hunter.freeze();
    }

    private void freezeHunters() {
        for (val hunter : game.getTeamManager().getHunters()) {
            if (hunter.isOnline()) {
                hunter.freeze();
            }
        }
    }

    private void unfreezeHunters() {
        for (val hunter : game.getTeamManager().getHunters()) {
            if (hunter.isOnline()) {
                hunter.unfreeze();
            }
        }
    }

    private void endPhase() {
        for (val hunter : game.getTeamManager().getHunters()) {
            if (hunter.isOnline()) {
                val player = hunter.getPlayer();

                SoundUtil.playStartSound(player);

                val textComponent = new TextComponent();
                textComponent.setText("GO!");
                textComponent.setColor(ChatColor.RED);

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
            }
        }

        phaseController.notifyPhaseEnded(PhaseType.HUNT);
    }

    private class CountdownTask extends BukkitRunnable {

        private int countdown = game.getSettings().getCountdown();

        public CountdownTask() {
            this.runTaskTimer(Manhunt.INSTANCE, 0L, 20L);
        }

        @Override
        public void run() {
            countdown--;

            if (countdown == -1) {
                endCountdown();
            } else {
                count();
            }
        }

        private void count() {
            for (val manhuntPlayer : game.getTeamManager().getPlayers()) {
                if (manhuntPlayer.isOnline()) {
                    val player = manhuntPlayer.getPlayer();

                    if (manhuntPlayer instanceof ManhuntHunter) {
                        SoundUtil.playTickSound(player);
                    }

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, formatCountdown());
                }
            }
        }

        private void endCountdown() {
            this.cancel();
            unfreezeHunters();
            endPhase();
        }

        private BaseComponent formatCountdown() {
            val textComponent = new TextComponent();
            textComponent.setText(Integer.toString(countdown));

            if (countdown <= 5) {
                textComponent.setColor(ChatColor.RED);
            } else if (countdown <= 15) {
                textComponent.setColor(ChatColor.GOLD);
            } else {
                textComponent.setColor(ChatColor.GREEN);
            }

            return textComponent;
        }
    }
}
