package me.primitive.manhunt;

import co.aikar.commands.PaperCommandManager;
import java.util.logging.Logger;
import me.primitive.manhunt.listener.ManhuntEventListener;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Manhunt extends JavaPlugin {

    public static Logger LOGGER;
    public static Plugin INSTANCE;
    public static World OVERWORLD;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        INSTANCE = this;
        OVERWORLD = getServer().getWorlds().get(0);

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new ManhuntCommand());

        registerListeners();
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new ManhuntEventListener(), this);
    }
}
