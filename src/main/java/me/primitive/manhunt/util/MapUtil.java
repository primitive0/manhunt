package me.primitive.manhunt.util;

import me.primitive.manhunt.ManhuntPlugin;
import org.bukkit.Bukkit;
import org.bukkit.map.MapView;

public class MapUtil {
    public static MapView createEmptyMapView() {
        MapView mapView = Bukkit.createMap(ManhuntPlugin.OVERWORLD);
        mapView.getRenderers().forEach(mapView::removeRenderer);
        return mapView;
    }
}
