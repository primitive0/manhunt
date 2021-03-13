package me.primitive.manhunt;

import me.primitive.manhunt.render.TrackerMapRenderer;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ManhuntPlugin extends JavaPlugin {
    public static Logger LOGGER;
    public static Plugin INSTANCE;
    public static World OVERWORLD;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        INSTANCE = this;
        OVERWORLD = getServer().getWorlds().get(0);

        registerListeners();
        getCommand("manhunt").setExecutor(new ManhuntCommand());

        ManhuntTrackerManager.init();
        TrackerMapRenderer.init();
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(
                new ManhuntEventListener(),
                this
        );
    }
}
/*
class EventListener implements Listener
{
    @EventHandler
    void onSomeEvent(PlayerInteractEntityEvent event)
    {
        if (event.getHand() == EquipmentSlot.HAND)
        {
            PlayerInventory playerInventory = event.getPlayer().getInventory();
            ItemStack itemInMainHand = playerInventory.getItemInMainHand();

            if (itemInMainHand.getType().equals(Material.BUCKET))
            {
                if (event.getRightClicked() instanceof Creeper)
                {
                    itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                    playerInventory.addItem(
                            new ItemStack(Material.MILK_BUCKET, 1)
                    );

                    Player player = event.getPlayer();
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 0.5f);
                    player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 0.8f, 1);
                }
            }
        }
    }

    @EventHandler
    void onPlayerRightClickBlock(PlayerInteractEvent event)
    {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND)
        {
            ItemStack myMap = new ItemStack(Material.FILLED_MAP, 1);
            MapMeta mapMeta = (MapMeta) myMap.getItemMeta();
            MapView mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));
            mapView.getRenderers().clear();

            for (MapRenderer renderer : mapView.getRenderers())
            {
                mapView.removeRenderer(renderer);
            }

            mapView.addRenderer(new MyMapRender());

            Bukkit.broadcastMessage(Integer.toString(mapView.getId()));
            Bukkit.broadcastMessage(mapView.getRenderers().toString());
            mapMeta.setMapView(mapView);
            myMap.setItemMeta(mapMeta);
            event.getPlayer().getInventory().addItem(myMap);
        }
    }
}*/
/*
class MyMapRender extends MapRenderer
{
    @Override
    public void render(MapView mapView, MapCanvas canvas, Player player)
    {
        canvas.drawText(10, 10, MinecraftFont.Font, "Hello world!");
    }
}*/