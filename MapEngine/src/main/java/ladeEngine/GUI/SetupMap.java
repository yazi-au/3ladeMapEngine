package ladeEngine.GUI;

import ladeEngine.MapEngine;
import ladeEngine.Monitor.PlaneMonitor;
import ladeEngine.Monitor.PlaneMonitorManager;
import ladeEngine.PlayerData.PlayerData;
import ladeEngine.PlayerData.types.LocationType;
import ladeEngine.Utils.M3IVector;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SetupMap implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType() == Material.ITEM_FRAME) {
                ItemFrame itemFrame = (ItemFrame) clickedBlock.getState();
                ItemStack item = itemFrame.getItem();
                if (item.getType() == Material.FILLED_MAP) {
                    PlayerData data = MapEngine.datasManager.search(player.getName());
                    Location a = ((LocationType)data.search("setUp1")).v;
                    if(a == null){
                        ((LocationType)data.search("setUp1")).v = clickedBlock.getLocation();
                    }else{
                        Location start = a;
                        Location end = clickedBlock.getLocation();
                        ((LocationType)data.search("setUp1")).v = null;
                        if(start.getBlockX()==end.getBlockX() || start.getBlockY()==end.getBlockY() || start.getBlockZ()==end.getBlockZ()){
                            if(!start.getWorld().getName().equals(end.getWorld().getName())){
                                player.sendMessage(ChatColor.RED + "You cant create a cross world application");
                                return;
                            }
                            int minX = Math.min(start.getBlockX(), end.getBlockX());
                            int maxX = Math.max(start.getBlockX(), end.getBlockX());
                            int minY = Math.min(start.getBlockY(), end.getBlockY());
                            int maxY = Math.max(start.getBlockY(), end.getBlockY());
                            int minZ = Math.min(start.getBlockZ(), end.getBlockZ());
                            int maxZ = Math.max(start.getBlockZ(), end.getBlockZ());
                            PlaneMonitor monitor = new PlaneMonitor();
                            monitor.face = itemFrame.getAttachedFace();
                            monitor.location = start;
                            for (int x = minX; x <= maxX; x++) {
                                for (int y = maxY; y >= minY; y--) {
                                    for (int z = minZ; z <= maxZ; z++) {
                                        Location location = new Location(start.getWorld(), x, y, z);
                                        if (location.getBlock().getType() != Material.AIR && clickedBlock.getType() == Material.ITEM_FRAME) {
                                            ItemFrame itemFrame1 = (ItemFrame) clickedBlock.getState();
                                            ItemStack item1 = itemFrame.getItem();
                                            if (item1.getType() == Material.FILLED_MAP) {
                                                monitor.relativeLocations.add(new M3IVector(x-minX,maxY-y,z-minZ));
                                            }else{
                                                player.sendMessage(ChatColor.RED + "You must choose a plane which fulled map!");
                                                monitor = null;
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                            PlaneMonitorManager.addMonitor(monitor);
                        }else{
                            player.sendMessage(ChatColor.RED + "You must choose on the same plane!");
                        }
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "You must choose a frame with a map!");
                }
            }
        }
    }
}
