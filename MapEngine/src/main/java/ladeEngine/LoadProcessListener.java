package ladeEngine;

import ladeEngine.Monitor.*;
import ladeEngine.PlayerData.types.RunningProcessListType;
import ladeEngine.Utils.BasicTools;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
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
                    HoldMonitor holdMonitor = new HoldMonitor(mapMeta.getMapView().getId());
                    RunningProcess rp = new RunningProcess(e.getPlayer(),app2,holdMonitor);
                    holdMonitor.cursor = new HoldCursor(rp);
                    app2.onEnable();
                    rp.renderProcess.update(rp.appData.maps.get(0));
                    rp.renderProcess.pushImage(rp.player,rp.monitor,null);
                    ((RunningProcessListType) MapEngine.datasManager.search(e.getPlayer().getName()).search("running")).v.add(rp);
                }else{
                    System.out.println("null");
                }
            }
        }
    }

    public static void monitorActiveTask() {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Player> players = BasicTools.getOnlinePlayers();
                for (Player player : players) {
                    ArrayList<PlaneMonitor> monitors = PlaneMonitorManager.getNearMonitor(player.getLocation(), MapEngine.loadRange);
                    ArrayList<RunningProcess> rps = ((RunningProcessListType) MapEngine.datasManager.search(player.getName()).search("running")).v;
                    rps.removeIf(rp -> !containA(monitors, rp.monitor) && shouldRemove(player, rp));
                    for (PlaneMonitor monitor : monitors) {
                        if (!containB(rps, monitor)) {
                            Application app = monitor.appBase.getApplication();
                            RunningProcess rp = new RunningProcess(player, app, monitor);
                            app.onEnable();
                            rp.renderProcess.update(rp.appData.maps.get(0));
                            rp.renderProcess.pushImage(rp.player, rp.monitor, new ArrayList<>());
                            rps.add(rp);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(MapEngine.getPlugin(MapEngine.class), 200L, 200L);
    }

    private static boolean shouldRemove(Player player, RunningProcess rp) {
        if (rp.monitor instanceof HoldMonitor) {
            int[] mapIds = getMapIds(player);
            for (int mapId : mapIds) {
                if (mapId == ((HoldMonitor) rp.monitor).mapId) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[] getMapIds(Player player) {
        ArrayList<Integer> mapIds = new ArrayList<>();

        addMapIdIfPresent(mapIds, player.getInventory().getItemInMainHand());
        addMapIdIfPresent(mapIds, player.getInventory().getItemInOffHand());

        return mapIds.stream().mapToInt(i -> i).toArray();
    }

    private static void addMapIdIfPresent(ArrayList<Integer> mapIds, ItemStack item) {
        if (item != null && item.getType() == Material.FILLED_MAP) {
            mapIds.add(((MapMeta) item.getItemMeta()).getMapView().getId());
        }
    }
    private static boolean containA(ArrayList<PlaneMonitor> a, Monitor b) {
        if (b instanceof PlaneMonitor) {
            PlaneMonitor c = (PlaneMonitor) b;
            for (PlaneMonitor monitor : a) {
                if (monitor.location.equals(c.location)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean containB(ArrayList<RunningProcess> a, Monitor b) {
        if (b instanceof PlaneMonitor) {
            PlaneMonitor c = (PlaneMonitor) b;
            for (RunningProcess process : a) {
                if (((PlaneMonitor)(process.monitor)).location.equals(c.location)) {
                    return true;
                }
            }
        }
        return false;
    }
}
