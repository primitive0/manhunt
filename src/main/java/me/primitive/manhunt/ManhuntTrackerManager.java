package me.primitive.manhunt;
//
//import java.util.Arrays;
//import lombok.val;
//import me.primitive.manhunt.containers.ManhuntHunter;
//import me.primitive.manhunt.containers.ManhuntPlayer;
//import me.primitive.manhunt.game.ManhuntGame;
//import me.primitive.manhunt.render.TrackerMapRenderer;
//import me.primitive.manhunt.util.ManhuntUtil;
//import me.primitive.manhunt.util.MapUtil;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.NamespacedKey;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.MapMeta;
//import org.bukkit.map.MapView;
//import org.bukkit.persistence.PersistentDataType;

/*public final class ManhuntTrackerManager {
    private static NamespacedKey TRACKER_KEY;

    private static ItemStack TRACKER_ITEM_TEMPLATE;

    public static void init() {
        TRACKER_KEY = new NamespacedKey(ManhuntPlugin.INSTANCE, "Tracker");

        TRACKER_ITEM_TEMPLATE = new ItemStack(Material.COMPASS, 1);

        val meta = (CompassMeta) TRACKER_ITEM_TEMPLATE.getItemMeta();
        meta.setLodestoneTracked(false);
        meta.getPersistentDataContainer().set(
                TRACKER_KEY,
                PersistentDataType.BYTE,
                (byte) 0
        );
        TRACKER_ITEM_TEMPLATE.setItemMeta(meta);
    }

    public static void giveTracker(ManhuntHunter hunter) {
        hunter.getPlayer().getInventory().addItem(TRACKER_ITEM_TEMPLATE);
    }

    public static void changeTrackerTarget(ManhuntHunter hunter, ManhuntPlayer trackedPlayer) {
        if (setTrackerTargetAndTryBound(hunter, trackedPlayer))
            hunter.getPlayer().sendMessage("Now pointing to " + trackedPlayer.getOfflinePlayer().getName());
    }

    public static boolean setTrackerTargetAndTryBound(ManhuntHunter hunter, ManhuntPlayer trackedPlayer) {
        setTrackerTarget(hunter, trackedPlayer);

        //hunter should be online

        val tracker = findTracker(hunter.getPlayer());
        if (tracker == null)
            return false;

        val tracked = hunter.trackedPlayer;

        if (tracked.isOnline() && hunter.getPlayer().getWorld().equals(tracked.getPlayer().getWorld()))
            ManhuntUtil.setCompassLodestoneLocation(tracker, tracked.getPlayer().getLocation());
        else
            ManhuntUtil.setCompassLodestoneLocation(tracker, null);

        return true;
    }

    public static void setTrackerTarget(ManhuntHunter hunter, ManhuntPlayer trackedPlayer) {
        hunter.trackedPlayer = trackedPlayer;
    }

    private static void trySetTrackerLocation(ManhuntHunter hunter, Location location) {
        val tracker = findTracker(hunter.getPlayer());
        if (tracker == null)
            return;

        ManhuntUtil.setCompassLodestoneLocation(tracker, location);
    }

    private static void updatePlayerLocationForHunter(ManhuntHunter hunter, Location newLocation) {
        val player = hunter.trackedPlayer;

        if (hunter.getPlayer().getWorld().equals(player.getPlayer().getWorld())) {
            trySetTrackerLocation(hunter, newLocation);
        } else {
            if (hunter.getPlayer().getWorld().equals(ManhuntPlugin.OVERWORLD)) {
                trySetTrackerLocation(hunter, player.lastOverworldLocation);
            } else {
                trySetTrackerLocation(hunter, null);
            }
        }
    }

    public static boolean isTracker(ItemStack item) {
        return item != null
                && item.getType() != Material.AIR
                && item.getItemMeta()
                .getPersistentDataContainer()
                .has(ManhuntTrackerManager.TRACKER_KEY, PersistentDataType.BYTE);
    }

    private static ItemStack findTracker(Player player) {
        val inv = player.getInventory();

        val mainHand = inv.getItemInMainHand();

        if (isTracker(mainHand))
            return mainHand;

        val offHand = inv.getItemInOffHand();

        if (isTracker(offHand))
            return offHand;

        return null;
    }

    public static void onManhuntPlayerMove(ManhuntPlayer player, Location newLocation) {
        Arrays.stream(ManhuntGame.getAliveHunters())
                .filter(hunter -> hunter.isOnline() && hunter.trackedPlayer == player)
                .forEach(hunter -> updatePlayerLocationForHunter(hunter, newLocation));
    }

    public static void onManhuntPlayerPortal(ManhuntPlayer player, Location oldLocation) {
        if (oldLocation.getWorld().equals(ManhuntPlugin.OVERWORLD))
            player.lastOverworldLocation = oldLocation;
        else
            player.lastOverworldLocation = null;

    }
}*/

public final class ManhuntTrackerManager {

//    private static NamespacedKey TRACKER_KEY;
//    private static ItemStack TRACKER_ITEM_TEMPLATE;
//
//    public static void init() {
//        TRACKER_KEY = new NamespacedKey(Manhunt.INSTANCE, "Tracker");
//
//        TRACKER_ITEM_TEMPLATE = new ItemStack(Material.FILLED_MAP, 1);
//
//        MapMeta meta = (MapMeta) TRACKER_ITEM_TEMPLATE.getItemMeta();
//
//        meta.getPersistentDataContainer().set(TRACKER_KEY, PersistentDataType.BYTE, (byte) 0);
//
//        MapView mapView = MapUtil.createEmptyMapView();
//        mapView.addRenderer(new TrackerMapRenderer());
//        meta.setMapView(mapView);
//
//        TRACKER_ITEM_TEMPLATE.setItemMeta(meta);
//    }
//
//    public static void giveTracker(ManhuntHunter hunter) {
//        hunter.getPlayer().getInventory().addItem(TRACKER_ITEM_TEMPLATE);
//    }
//
//    public static void changeTrackerTarget(ManhuntHunter hunter, ManhuntPlayer trackedPlayer) {
//        if (setTrackerTargetAndTryBound(hunter, trackedPlayer)) {
//            hunter.getPlayer().sendMessage("Now pointing to " + trackedPlayer.getOfflinePlayer().getName());
//        }
//    }
//
//    public static boolean setTrackerTargetAndTryBound(ManhuntHunter hunter, ManhuntPlayer trackedPlayer) {
//        setTrackerTarget(hunter, trackedPlayer);
//
//        //hunter should be online
//
//        val tracker = findTracker(hunter.getPlayer());
//        if (tracker == null) {
//            return false;
//        }
//
//        val tracked = hunter.trackedPlayer;
//
//        if (tracked.isOnline() && hunter.getPlayer().getWorld().equals(tracked.getPlayer().getWorld())) {
//            ManhuntUtil.setCompassLodestoneLocation(tracker, tracked.getPlayer().getLocation());
//        } else {
//            ManhuntUtil.setCompassLodestoneLocation(tracker, null);
//        }
//
//        return true;
//    }
//
//    public static void setTrackerTarget(ManhuntHunter hunter, ManhuntPlayer trackedPlayer) {
//        hunter.trackedPlayer = trackedPlayer;
//    }
//
//    private static void trySetTrackerLocation(ManhuntHunter hunter, Location location) {
//        val tracker = findTracker(hunter.getPlayer());
//        if (tracker == null) {
//            return;
//        }
//
//        ManhuntUtil.setCompassLodestoneLocation(tracker, location);
//    }
//
//    private static void updatePlayerLocationForHunter(ManhuntHunter hunter, Location newLocation) {
//        val player = hunter.trackedPlayer;
//
//        if (hunter.getPlayer().getWorld().equals(player.getPlayer().getWorld())) {
//            trySetTrackerLocation(hunter, newLocation);
//        } else if (hunter.getPlayer().getWorld().equals(Manhunt.OVERWORLD)) {
//            trySetTrackerLocation(hunter, player.lastOverworldLocation);
//        } else {
//            trySetTrackerLocation(hunter, null);
//        }
//
//    }
//
//    public static boolean isTracker(ItemStack item) {
//        return item != null
//            && item.getType() != Material.AIR
//            && item.getItemMeta()
//            .getPersistentDataContainer()
//            .has(ManhuntTrackerManager.TRACKER_KEY, PersistentDataType.BYTE);
//    }
//
//    private static ItemStack findTracker(Player player) {
//        val inv = player.getInventory();
//
//        val mainHand = inv.getItemInMainHand();
//
//        if (isTracker(mainHand)) {
//            return mainHand;
//        }
//
//        val offHand = inv.getItemInOffHand();
//
//        if (isTracker(offHand)) {
//            return offHand;
//        }
//
//        return null;
//    }
//
//    public static void onManhuntPlayerMove(ManhuntPlayer player, Location newLocation) {
//        Arrays.stream(ManhuntGame.getAliveHunters())
//            .filter(hunter -> hunter.isOnline() && hunter.trackedPlayer == player)
//            .forEach(hunter -> updatePlayerLocationForHunter(hunter, newLocation));
//    }
//
//    public static void onManhuntPlayerPortal(ManhuntPlayer player, Location oldLocation) {
//        if (oldLocation.getWorld().equals(Manhunt.OVERWORLD)) {
//            player.lastOverworldLocation = oldLocation;
//        } else {
//            player.lastOverworldLocation = null;
//        }
//
//    }
}
