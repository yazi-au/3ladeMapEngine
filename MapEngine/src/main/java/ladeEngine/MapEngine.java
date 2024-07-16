package ladeEngine;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import ladeEngine.Monitor.CursorListener;
import ladeEngine.PlayerData.types.RunningProcessListType;
import ladeEngine.PlayerData.types.StringType;
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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.MapMeta;
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
        getServer().getPluginManager().registerEvents(new CursorListener(),this);
        saveDefaultConfig();
        path = getDataFolder()+"\\";
        PlaneMonitorManager.init();
        datasManager = new DatasManager(getDataFolder() + "\\players\\",this);
        datasManager.baseData.add(new BoolType("setUpEnable",false,false));
        datasManager.baseData.add(new StringType("setUpApp",null,false));
        datasManager.baseData.add(new LocationType("setUp1",null,false));
        datasManager.baseData.add(new RunningProcessListType("running",new ArrayList<>(),false));
        datasManager.baseData.add(new RunningProcessListType("holdRP",new ArrayList<>(),false));
        basicEvents.add("MouseMoveEvent");
        registerApp(new TestApplication());
        LoadProcessListener.monitorActiveTask(); //Run scan task
        blockDefaultPacket();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void blockDefaultPacket() {
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.MAP) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                byte scale = packet.getBytes().read(0);
                int id = packet.getIntegers().read(0);
                if (scale <= 4) {
                    if (player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                        if (player.getInventory().getItemInMainHand().getType().equals(Material.FILLED_MAP)) {
                            if (player.getInventory().getItemInMainHand().getItemMeta().getLore().get(player.getInventory().getItemInMainHand().getItemMeta().getLore().size() - 1).startsWith("||")) {
                                MapMeta mapMeta = (MapMeta) player.getInventory().getItemInMainHand().getItemMeta();
                                if(mapMeta.getMapView().getId() == id) {
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }

                    if (player.getInventory().getItemInOffHand().hasItemMeta() && player.getInventory().getItemInOffHand().getItemMeta().hasLore()) {
                        if (player.getInventory().getItemInOffHand().getType().equals(Material.FILLED_MAP)) {
                            if (player.getInventory().getItemInOffHand().getItemMeta().getLore().get(player.getInventory().getItemInOffHand().getItemMeta().getLore().size() - 1).startsWith("||")) {
                                MapMeta mapMeta = (MapMeta) player.getInventory().getItemInMainHand().getItemMeta();
                                if(mapMeta.getMapView().getId() == id) {
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }
                } else {
                    packet.getBytes().write(0, (byte) (scale - 4));
                }
            }
        });
    }

    public static Application searchApp(String name){
        for (int i = 0; i < apps.size(); i++) {
            if(apps.get(i).name.equals(name)){
                return apps.get(i);
            }
        }
        return null;
    }

    public static void registerApp(Application app){
        apps.add(app);
    }
    public static void unregisterApp(String name){
        Application app = searchApp(name);
        if(app != null){
            apps.remove(app);
        }
    }
}
