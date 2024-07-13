package ladeEngine.Monitor;

import ladeEngine.MapEngine;
import ladeEngine.Utils.GlobalFileTool;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.meta.MapMeta;

import java.io.File;
import java.util.ArrayList;

public class PlaneSaveListener implements Listener {
    @EventHandler
    public void onLoad(ChunkLoadEvent e){
        File file = new File(MapEngine.path+"chunkDatas\\"+e.getChunk().getX()+"|"+e.getChunk().getZ());
        if(file.exists()){
            ArrayList<String> inner = GlobalFileTool.read(file);
            for (int i = 0; i < inner.size(); i++) {
                PlaneMonitor monitor = new PlaneMonitor(e.getChunk().getX(),e.getChunk().getZ());
                monitor.load(inner.get(i));
                PlaneMonitorManager.addMonitor(monitor);
            }
        }
    }
    @EventHandler
    public void onUnload(ChunkUnloadEvent e){
        ArrayList<String> inner = new ArrayList<>();
        for (int i = 0; i < PlaneMonitorManager.activeMonitors.size(); i++) {
            if(PlaneMonitorManager.activeMonitors.get(i).chunkX==e.getChunk().getX()&&
            PlaneMonitorManager.activeMonitors.get(i).chunkY==e.getChunk().getZ()){
                inner.add(PlaneMonitorManager.activeMonitors.get(i).save());
                PlaneMonitorManager.activeMonitors.remove(i);
                i--;
            }
        }
        GlobalFileTool.write(new File(MapEngine.path+"chunkDatas\\"+e.getChunk().getX()+"|"+e.getChunk().getZ()),inner);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ITEM_FRAME) {
            ItemFrame itemFrame = (ItemFrame) event.getEntity();
            if (itemFrame.getItem().getType() == Material.FILLED_MAP) {
                MapMeta mapMeta = (MapMeta) itemFrame.getItem().getItemMeta();
                PlaneMonitorManager.isActiveMapID(mapMeta.getMapView().getId());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.ITEM_FRAME) {
            ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
            if (itemFrame.getItem().getType() == Material.FILLED_MAP) {
                if(MapEngine.cantRotateMap){
                    event.setCancelled(true);
                    return;
                }
                MapMeta mapMeta = (MapMeta) itemFrame.getItem().getItemMeta();
                PlaneMonitorManager.isActiveMapID(mapMeta.getMapView().getId());
                event.setCancelled(true);
            }
        }
    }
}