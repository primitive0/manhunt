package me.primitive.manhunt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.primitive.manhunt.management.GameplayManager;

public class StartGameCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        GameplayManager.getInstance().startGame();
        return false;
    }
}
