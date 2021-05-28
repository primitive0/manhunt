package me.primitive.manhunt.commands;

import me.primitive.manhunt.management.GameplayManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartGameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GameplayManager.getInstance().startGame();
        return false;
    }
}
