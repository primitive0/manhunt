package me.primitive.manhunt.management;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class IsPlayersReadyChecker
{
    private static class VotesContainer
    {
        public int readyPlayers;
        public int allPlayers;
    }

    private static IsPlayersReadyChecker INSTANCE;

    private VotesContainer container;

    private BaseComponent[] messageVoteStartInitiator;
    private BaseComponent[] messageVoteStartOthers;

    public static IsPlayersReadyChecker getInstance()
    {
        return INSTANCE == null
                ? INSTANCE = new IsPlayersReadyChecker()
                : INSTANCE;
    }

    private IsPlayersReadyChecker()
    {
    }

    private void buildChatMessages()
    {
        messageVoteStartInitiator = new ComponentBuilder()
                .bold(true).append("*ожидание готовности других игроков*\n")
                .color(ChatColor.RED).append("               [Я не готов!]")
                .create();

        messageVoteStartOthers = new ComponentBuilder()
                .bold(true).append("Потвердите готовность.")
                .append("\n")
                .color(ChatColor.RED).append("    [Я готов!]")
                .color(ChatColor.GREEN).append("   [Я не готов!]")
                .create();
    }

    public void initVote(Player initiator)
    {
        container = new VotesContainer();
        container.allPlayers = GameTeamManager.INSTANCE.pvhPlayers.size();
        container.readyPlayers++;

        notifyVoteStart(initiator);
    }

    public void iAmReady(Player player)
    {
        if (!isRunning())
            throw new IllegalStateException();

        if (container.readyPlayers == container.allPlayers)
            onAllReady();
        else
        {
            container.readyPlayers++;
            notifySomeoneIsReady(player);
        }

    }

    public void iAmNotReady(Player player)
    {
        container = null;
    }

    private void onAllReady()
    {

    }

    private void notifyVoteStart(Player initiator)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player == initiator)
            {
                player.spigot().sendMessage(messageVoteStartInitiator);
                continue;
            }

            player.spigot().sendMessage(messageVoteStartOthers);
        }
    }

    private void notifySomeoneIsReady(Player player)
    {

    }

    private void notifyVoteFailure()
    {

    }

    public boolean isRunning()
    {
        return container != null;
    }
}
