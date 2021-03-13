package me.primitive.manhunt;

import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ManhuntCommand implements CommandExecutor {
    public static ItemStack cache = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command");
            return true;
        }

        if (args.length == 0)
            return false;

        val player = ((Player) sender);
        val commandType = args[0];
        byte commandTypeCached;

        switch (commandType) {
            case "join":
                joinTeam(player, args);
                return true;

            case "start":
                commandTypeCached = 1;
                break;

            case "stop":
                commandTypeCached = 2;
                break;

            case "assemble":
                commandTypeCached = 3;
                break;

            case "countdown":
                commandTypeCached = 4;
                break;

            default:
                return false;
        }

        if (player.isOp()) {
            switch (commandTypeCached) {
                case 1:
                    if (!ManhuntGame.startGame()) {
                        player.sendMessage("Game has already started");
                    }
                    break;

                case 2:
                    if (!ManhuntGame.stopGame()) {
                        player.sendMessage("Nothing to stop");
                    }
                    break;

                case 3:
                    assemble(player);
                    break;

                case 4:
                    setCountdown(player, args);
                    break;
            }
        } else {
            player.sendMessage("You must be OP to use this command");
        }

        return true;
    }

    private static void joinTeam(Player sender, String[] args) {
        val teamName = args[1];

        switch (teamName) {
            case "players":
                ManhuntGame.joinPlayers(sender);
                break;

            case "hunters":
                ManhuntGame.joinHunters(sender);
                break;

            case "none":
                ManhuntGame.leaveTeam(sender);

            default:
                sender.sendMessage("This team doesn't exist");
                break;
        }
    }

    private static void assemble(Player sender) {
        if (ManhuntGame.isGameRunning()) {
            sender.sendMessage("Cannot assemble players while game running!");
            return;
        }

        val spawn = ManhuntPlugin.OVERWORLD.getSpawnLocation();

        for (val player : Bukkit.getOnlinePlayers()) {
            player.teleport(spawn);

            if (!player.equals(sender))
                player.setGameMode(GameMode.ADVENTURE);
        }
    }

    private static void setCountdown(Player sender, String[] args) {
        try {
            val res = ManhuntGame.setCountdown(
                    Integer.parseInt(args[1])
            );

            if (!res) {
                sender.sendMessage("Wrong countdown value!");
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("Wrong number format!");
        }
    }
}
