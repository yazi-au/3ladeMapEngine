package ladeEngine.GUI;

import ladeEngine.MapEngine;
import ladeEngine.Monitor.PlaneMonitor;
import ladeEngine.Monitor.PlaneMonitorManager;
import ladeEngine.PlayerData.PlayerData;
import ladeEngine.PlayerData.types.BoolType;
import ladeEngine.PlayerData.types.LocationType;
import ladeEngine.Utils.M3IVector;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

public class SetupMap implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedBlock = event.getRightClicked();
        if (clickedBlock != null && clickedBlock.getType().equals(EntityType.ITEM_FRAME)) {
            PlayerData data = MapEngine.datasManager.search(player.getName());
            ItemFrame itemFrame = (ItemFrame) clickedBlock;
            ItemStack item = itemFrame.getItem();
            if (item.getType() == Material.FILLED_MAP) {
                if (!((BoolType) data.search("setUpEnable")).v) return;
                Location a = ((LocationType) data.search("setUp1")).v;
                if (a == null) {
                    ((LocationType) data.search("setUp1")).v = clickedBlock.getLocation();
                    player.sendMessage(ChatColor.AQUA + "Please Click Second Map Block");
                } else {
                    Location start = a;
                    Location end = clickedBlock.getLocation();
                    ((LocationType) data.search("setUp1")).v = null;
                    if (start.getBlockX() == end.getBlockX() || start.getBlockY() == end.getBlockY() || start.getBlockZ() == end.getBlockZ()) {
                        if (!start.getWorld().getName().equals(end.getWorld().getName())) {
                            player.sendMessage(ChatColor.RED + "You cant create a cross world application");
                            ((BoolType) data.search("setUpEnable")).v = false;
                            return;
                        }
                        int minX = Math.min(start.getBlockX(), end.getBlockX());
                        int maxX = Math.max(start.getBlockX(), end.getBlockX());
                        int minY = Math.min(start.getBlockY(), end.getBlockY());
                        int maxY = Math.max(start.getBlockY(), end.getBlockY());
                        int minZ = Math.min(start.getBlockZ(), end.getBlockZ());
                        int maxZ = Math.max(start.getBlockZ(), end.getBlockZ());
                        PlaneMonitor monitor = new PlaneMonitor(start.getChunk().getX(),start.getChunk().getZ());
                        monitor.face = itemFrame.getAttachedFace();
                        monitor.location = new Location(start.getWorld(),minX,maxY,minZ);
                        if(maxX-minX == 0){
                            monitor.w = maxZ-minZ;
                            monitor.h = maxY-minY;
                        }else if(maxZ-minZ == 0){
                            monitor.w = maxX-minX;
                            monitor.h = maxY-minY;
                        }else{
                            monitor.w = maxX-minX;
                            monitor.h = maxZ-minZ;
                        }
                        for (int x = minX; x <= maxX; x++) {
                            for (int y = maxY; y >= minY; y--) {
                                for (int z = minZ; z <= maxZ; z++) {
                                    Location location = new Location(start.getWorld(), x, y, z);
                                    if (getItemFrameOnBlock(location) != null) {
                                        ItemFrame itemFrame1 = getItemFrameOnBlock(location);
                                        ItemStack item1 = itemFrame1.getItem();
                                        if (item1.getType() == Material.FILLED_MAP) {
                                            MapMeta mapMeta = (MapMeta) item1.getItemMeta();
                                            monitor.mapIDs.add(mapMeta.getMapView().getId());
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You must choose a plane which fulled map!");
                                            ((BoolType) data.search("setUpEnable")).v = false;
                                            monitor = null;
                                            return;
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You must choose a plane which fulled map!");
                                        ((BoolType) data.search("setUpEnable")).v = false;
                                        monitor = null;
                                        return;
                                    }
                                }
                            }
                        }
                        PlaneMonitorManager.addMonitor(monitor);
                        player.sendMessage(ChatColor.DARK_GREEN + "Successful set up this application! Count:"+monitor.mapIDs.size());
                        ((BoolType) data.search("setUpEnable")).v = false;
                    } else {
                        player.sendMessage(ChatColor.RED + "You must choose on the same plane!");
                        ((BoolType) data.search("setUpEnable")).v = false;
                    }
                }
            }
        }
    }
    public ItemFrame getItemFrameOnBlock(Location location) {
        World world = location.getWorld();
        if (world == null) {
            return null;
        }
        for (Entity entity : world.getNearbyEntities(location, 1, 1, 1)) {
            if (entity instanceof ItemFrame) {
                ItemFrame itemFrame = (ItemFrame) entity;
                if (itemFrame.getLocation().getBlockX() == location.getBlockX() &&
                        itemFrame.getLocation().getBlockY() == location.getBlockY() &&
                        itemFrame.getLocation().getBlockZ() == location.getBlockZ()) {
                    return itemFrame;
                }
            }
        }
        return null;
    }
}
