package me.primitive.manhunt.util;

import lombok.val;
import me.primitive.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;

public class MapUtil {

    public static ItemStack createMapWithRenderer(MapRenderer renderer) {
        val map = new ItemStack(Material.FILLED_MAP, 1);

        val view = Bukkit.createMap(Manhunt.OVERWORLD);
        view.getRenderers().forEach(view::removeRenderer);
        view.addRenderer(renderer);

        val meta = (MapMeta) map.getItemMeta();
        meta.setMapView(view);

        map.setItemMeta(meta);
        return map;
    }
}
