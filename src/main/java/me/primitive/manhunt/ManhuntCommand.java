package me.primitive.manhunt;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import lombok.val;
import me.primitive.manhunt.game.ManhuntGame;
import me.primitive.manhunt.game.TeamManager.TeamDropResult;
import me.primitive.manhunt.game.TeamManager.TeamPutResult;
import org.bukkit.entity.Player;

@CommandAlias("manhunt")
public final class ManhuntCommand extends BaseCommand {

    @Subcommand("start")
    public void startGame(Player player) {
        val result = ManhuntGame.getInstance().start();

        switch (result) {
            case OK:
                player.sendMessage("ok");
                break;

            case NOT_ENOUGH_PLAYERS:
                player.sendMessage("not enough players");
                break;

            case ALREADY_STARTED:
                player.sendMessage("already started");
                break;
        }
    }

    @Subcommand("join")
    public void joinTeam(Player player, String team) {
        val game = ManhuntGame.getInstance();

        if (game.isRunning()) {
            player.sendMessage("game is running");
            return;
        }

        val teamManager = game.getTeamManager();
        TeamPutResult putResult;
        switch (team.toLowerCase()) {
            case "speedrunners":
                putResult = teamManager.putSpeedrunner(player);
                break;

            case "hunters":
                putResult = teamManager.putHunter(player);
                break;

            default:
                player.sendMessage("no team");
                return;
        }

        switch (putResult) {
            case OK_SPEEDRUNNER:
                player.sendMessage("joined speedrunners");
                break;

            case OK_HUNTER:
                player.sendMessage("joined hunters");
                break;

            case ALREADY_IN:
                player.sendMessage("already in");
                break;
        }
    }

    @Subcommand("leave")
    public void leaveTeam(Player player) {
        val game = ManhuntGame.getInstance();

        if (game.isRunning()) {
            player.sendMessage("game is running");
            return;
        }

        val result = game.getTeamManager().dropManhuntPlayer(player);
        if (result == TeamDropResult.OK) {
            player.sendMessage("you have left team");
        } else {
            player.sendMessage("you was not in any team");
        }
    }

    @Subcommand("stop")
    public void stopGame(Player player) {
        val game = ManhuntGame.getInstance();

        if (game.isRunning()) {
            game.forceStop();
            player.sendMessage("stopped");
        } else {
            player.sendMessage("game is not running");
        }
    }
}
