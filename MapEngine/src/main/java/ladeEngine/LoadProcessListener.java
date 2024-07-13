package ladeEngine;

import ladeEngine.Monitor.HoldMonitor;
import ladeEngine.PlayerData.types.RunningProcessType;
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
        if(e.getPlayer().getInventory().getItem(e.getNewSlot()).getType() != Material.FILLED_MAP) return;
        ItemMeta meta = Objects.requireNonNull(e.getPlayer().getInventory().getItem(e.getNewSlot())).getItemMeta();
        if(meta.getLore() != null && meta.getLore().size() >= 1){
            if(meta.getLore().get(meta.getLore().size()).startsWith("||")){
                Application app = MapEngine.searchApp(meta.getLore().get(meta.getLore().size()).substring(2));
                if(app != null){
                    MapMeta mapMeta = (MapMeta) e.getPlayer().getInventory().getItem(e.getNewSlot()).getItemMeta();
                    RunningProcess rp = new RunningProcess(app,new HoldMonitor(mapMeta.getMapView().getId()));
                    ((RunningProcessType)MapEngine.datasManager.search(e.getPlayer().getName()).search("running")).v.add(rp);
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
                }
            }
        }.runTaskTimerAsynchronously(MapEngine.getPlugin(MapEngine.class),2000L,2000L);
    }
}
