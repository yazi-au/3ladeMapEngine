package ladeEngine;

import ladeEngine.PlayerData.DatasManager;
import ladeEngine.PlayerData.types.IntType;
import ladeEngine.PlayerData.types.LocationType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public final class MapEngine extends JavaPlugin {
    public static ArrayList<String> basicEvents = new ArrayList<>();
    public static DatasManager datasManager;
    public static ArrayList<Application> apps = new ArrayList<>();
    public static Logger log;
    @Override
    public void onEnable() {
        log = getLogger();
        log.info(ChatColor.AQUA + "3lade Map Engine launching......");
        saveDefaultConfig();
        datasManager = new DatasManager(getDataFolder() + "\\players\\",this);
        datasManager.baseData.add(new LocationType("setUp1",null,false));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
