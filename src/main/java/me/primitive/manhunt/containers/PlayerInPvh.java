package me.primitive.manhunt.containers;

import org.bukkit.entity.Player;

public abstract class PlayerInPvh {

    protected Player bukkitPlayer;

    protected PlayerInPvh(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }
}
