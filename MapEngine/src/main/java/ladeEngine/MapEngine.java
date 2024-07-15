package ladeEngine;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import ladeEngine.PlayerData.types.RunningProcessType;
import ladeEngine.Test.TestApplication;
import ladeEngine.GUI.AppListCommand;
import ladeEngine.GUI.GUIListener;
import ladeEngine.GUI.SetupMap;
import ladeEngine.Monitor.PlaneMonitorManager;
import ladeEngine.Monitor.PlaneSaveListener;
import ladeEngine.PlayerData.DatasManager;
import ladeEngine.PlayerData.types.BoolType;
import ladeEngine.PlayerData.types.LocationType;
import ladeEngine.Utils.BasicTools;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public final class MapEngine extends JavaPlugin {
    public static ArrayList<String> basicEvents = new ArrayList<>();
    public static DatasManager datasManager;
    public static ArrayList<Application> apps = new ArrayList<>();
    public static Logger log;
    public static String path;
    public static boolean cantRotateMap = false;
    public static int loadRange = 10;
    public static ProtocolManager protocolManager;
    @Override
    public void onEnable() {
        BasicTools.init();
        log = getLogger();
        log.info(ChatColor.AQUA + "3lade Map Engine launching......");
        protocolManager = ProtocolLibrary.getProtocolManager();
        getCommand("meapps").setExecutor(new AppListCommand());
        getServer().getPluginManager().registerEvents(new GUIListener(),this);
        getServer().getPluginManager().registerEvents(new SetupMap(),this);
        getServer().getPluginManager().registerEvents(new PlaneSaveListener(),this);
        getServer().getPluginManager().registerEvents(new LoadProcessListener(),this);
        saveDefaultConfig();
        path = getDataFolder()+"\\";
        PlaneMonitorManager.init();
        datasManager = new DatasManager(getDataFolder() + "\\players\\",this);
        datasManager.baseData.add(new BoolType("setUpEnable",false,false));
        datasManager.baseData.add(new LocationType("setUp1",null,false));
        datasManager.baseData.add(new RunningProcessType("running",new ArrayList<>(),false));
        apps.add(new TestApplication());
        LoadProcessListener.monitorActiveTask(); //Run scan task
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Application searchApp(String name){
        for (int i = 0; i < apps.size(); i++) {
            if(apps.get(i).name.equals(name)){
                return apps.get(i);
            }
        }
        return null;
    }
}
