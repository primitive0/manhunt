package me.primitive.manhunt.management;

import me.primitive.manhunt.containers.PlayerInPvh;
import me.primitive.manhunt.containers.PvhHunter;

public class GameplayManager
{
    private static GameplayManager INSTANCE;

    public static GameplayManager getInstance()
    {
        return INSTANCE == null
                ? INSTANCE = new GameplayManager()
                : INSTANCE;
    }

    private boolean playing = false;

    private GameplayManager()
    {
    }

    public void startGame()
    {
        GameTeamManager.INSTANCE.setLock(true);
        playing = true;


    }

    public void freezeHunters()
    {
        for (PlayerInPvh pvhPlayer : GameTeamManager.INSTANCE.pvhPlayers)
        {
            if (pvhPlayer instanceof PvhHunter)
            {
            }
        }
    }

    //Don't use.
    public void forceEnd()
    {
        playing = false;
    }

    public boolean isGameGoing()
    {
        return playing;
    }
}
