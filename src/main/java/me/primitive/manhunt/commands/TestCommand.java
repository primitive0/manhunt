package me.primitive.manhunt.commands;

import me.primitive.manhunt.map.util.CompassMapRenderer;
import me.primitive.manhunt.util.MapUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player playerSender = (Player) sender;

            ItemStack myMap = new ItemStack(Material.FILLED_MAP, 1);
            MapMeta mapMeta = (MapMeta) myMap.getItemMeta();

            MapView mapView = MapUtil.createEmptyMapView();
            mapView.addRenderer(new CompassMapRenderer(Bukkit.getPlayer(args[0])));
            mapMeta.setMapView(mapView);

            myMap.setItemMeta(mapMeta);

            playerSender.getInventory().addItem(myMap);

            return true;
        }

        return false;

        /*
        Bukkit.broadcastMessage(
                PvhTeamManager.INSTANCE.pvhPlayers.toString()
        );*/
        /*
        Bukkit.broadcastMessage(
                "Players: " + PvhTeamManager.INSTANCE.playersTeam.getEntries().toString()
                + "\n" +
                "Hunters: " + PvhTeamManager.INSTANCE.huntersTeam.getEntries().toString()
        );
        */

        /*
        StartGameVoteManager.getInstance().startVote((Player) sender);

        return true;*/
    }
}
