package me.primitive.manhunt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.primitive.manhunt.management.GameTeamManager;

public class IAmCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) //TODO: add chat messages
    {
        if (sender instanceof Player) {
            if (args.length != 1) return false;

            Player playerSender = (Player) sender;

            String role = args[0];

            GameTeamManager.RegisteredAs registeredAs = GameTeamManager.INSTANCE.whoIsPlayer(playerSender);

            switch (role) {
                case "player":
                    if (registeredAs == GameTeamManager.RegisteredAs.PLAYER) {
                        playerSender.sendMessage("Ты уже в этой команде.");
                        return true;
                    }

                    if (registeredAs == GameTeamManager.RegisteredAs.HUNTER) {
                        GameTeamManager.INSTANCE.removePlayer(playerSender);
                        //playerSender.sendMessage("Ваша команда была изменена.");
                    }

                    GameTeamManager.INSTANCE.addPvhPlayer(playerSender);

                    return true;

                case "hunter":
                    if (registeredAs == GameTeamManager.RegisteredAs.HUNTER) {
                        playerSender.sendMessage("Ты уже в этой команде.");
                        return true;
                    }

                    if (registeredAs == GameTeamManager.RegisteredAs.PLAYER) {
                        GameTeamManager.INSTANCE.removePlayer(playerSender);
                        //playerSender.sendMessage("Ваша команда была изменена.");
                    }

                    GameTeamManager.INSTANCE.addPvhHunter(playerSender);

                    return true;
            }
        }

        return false;
    }
}
