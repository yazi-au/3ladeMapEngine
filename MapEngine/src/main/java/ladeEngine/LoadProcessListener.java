package ladeEngine;

import ladeEngine.Monitor.HoldMonitor;
import ladeEngine.Monitor.PlaneMonitor;
import ladeEngine.Monitor.PlaneMonitorManager;
import ladeEngine.PlayerData.types.RunningProcessType;
import ladeEngine.Render.RenderProcess;
import ladeEngine.Utils.BasicTools;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Objects;

public class LoadProcessListener implements Listener {
    @EventHandler
    public void onSwap(PlayerItemHeldEvent e){
        if(e.getPlayer().getInventory().getItem(e.getNewSlot()) == null) return;
        if(e.getPlayer().getInventory().getItem(e.getNewSlot()).getType() != Material.FILLED_MAP) return;
        ItemMeta meta = Objects.requireNonNull(e.getPlayer().getInventory().getItem(e.getNewSlot())).getItemMeta();
        if(meta.getLore() != null && meta.getLore().size() >= 1){
            if(meta.getLore().get(meta.getLore().size()-1).startsWith("||")){
                Application app = MapEngine.searchApp(meta.getLore().get(meta.getLore().size()-1).substring(2));
                if(app != null){
                    MapMeta mapMeta = (MapMeta) e.getPlayer().getInventory().getItem(e.getNewSlot()).getItemMeta();
                    Application app2 = app.getApplication();
                    RunningProcess rp = new RunningProcess(e.getPlayer(),app2,new HoldMonitor(mapMeta.getMapView().getId()));
                    app2.onEnable();
                    rp.renderProcess.update(rp.appData.maps.get(0));
                    rp.renderProcess.pushImage(rp.player,rp.hold);
                    ((RunningProcessType)MapEngine.datasManager.search(e.getPlayer().getName()).search("running")).v.add(rp);
                }else{
                    System.out.println("null");
                }
            }
        }
    }

    public static void monitorActiveTask(){
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Player> players = BasicTools.getOnlinePlayers();
                for (int i = 0; i < players.size(); i++) {
                    ArrayList<PlaneMonitor> monitors = PlaneMonitorManager.getNearMonitor(players.get(i).getLocation(), MapEngine.loadRange);
                    System.out.println("Near Monitors: " + monitors.size());
                    ArrayList<RunningProcess> rps = ((RunningProcessType) MapEngine.datasManager.search(players.get(i).getName()).search("running")).v;

                    for (int j = 0; j < rps.size(); j++) {
                        if (rps.get(j).plane == null || !containA(monitors, rps.get(j).plane)) {
                            rps.remove(j);
                            j--;
                        }
                    }
                    for (int j = 0; j < monitors.size(); j++) {
                        if (!containB(rps, monitors.get(j))) {
                            Application app = monitors.get(j).appBase.getApplication();
                            RunningProcess rp = new RunningProcess(players.get(i), app, monitors.get(j));
                            rps.add(rp);
                            app.onEnable();
                            rp.renderProcess.update(rp.appData.maps.get(0));
                            rp.renderProcess.pushImage(rp.player, rp.plane);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(MapEngine.getPlugin(MapEngine.class),200L,200L);
    }
    private static boolean containA(ArrayList<PlaneMonitor> a, PlaneMonitor b) {
        for (PlaneMonitor monitor : a) {
            if (monitor.location.equals(b.location)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containB(ArrayList<RunningProcess> a, PlaneMonitor b) {
        for (RunningProcess process : a) {
            if (process.plane.location.equals(b.location)) {
                return true;
            }
        }
        return false;
    }
}
