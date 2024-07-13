package ladeEngine.Monitor;

import ladeEngine.MapEngine;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlaneMonitorManager {
    public static ArrayList<PlaneMonitor> activeMonitors = new ArrayList<>(); //link list
    public static void addMonitor(PlaneMonitor monitor){
        activeMonitors.add(monitor);
    }
    public static void init(){
        File file = new File(MapEngine.path+"chunksDatas"+"\\");
        if(!file.exists()) {
            file.mkdirs();
        }
    }
    public static boolean isActiveMapID(int id){
        for (int i = 0; i < activeMonitors.size(); i++) {
            ArrayList<Integer> mapIDs = activeMonitors.get(i).mapIDs;
            for (int j = 0; j < mapIDs.size(); j++) {
                if(mapIDs.get(i) == id) return true;
            }
        }
        return false;
    }
    public static ArrayList<PlaneMonitor> getNearMonitor(Location location,int range){
        ArrayList<PlaneMonitor> planeMonitors = new ArrayList<>();
        int distance = 0;
        int x = 0,y = 0,z = 0;
        for (int i = 0; i < activeMonitors.size(); i++) {
            switch(activeMonitors.get(i).face){
                case EAST:
                case WEST:
                    x = Math.abs(location.getBlockX()-activeMonitors.get(i).location.getBlockX());
                    y = location.getBlockY() > activeMonitors.get(i).location.getBlockY() ? location.getBlockY() - activeMonitors.get(i).location.getBlockY() :
                            location.getBlockY() < activeMonitors.get(i).location.getBlockY()-activeMonitors.get(i).h ? location.getBlockY() - activeMonitors.get(i).location.getBlockY()+activeMonitors.get(i).h : 0;
                    z = location.getBlockZ() < activeMonitors.get(i).location.getBlockZ() ? location.getBlockZ() - activeMonitors.get(i).location.getBlockZ() :
                            location.getBlockZ() > activeMonitors.get(i).location.getBlockZ()+activeMonitors.get(i).w ? location.getBlockZ() - activeMonitors.get(i).location.getBlockZ()-activeMonitors.get(i).w : 0;
                    distance = (int) Math.sqrt(x*x + y*y + z*z);
                case SOUTH:
                case NORTH:
                    z = Math.abs(location.getBlockZ()-activeMonitors.get(i).location.getBlockZ());
                    y = location.getBlockY() > activeMonitors.get(i).location.getBlockY() ? location.getBlockY() - activeMonitors.get(i).location.getBlockY() :
                            location.getBlockY() < activeMonitors.get(i).location.getBlockY()-activeMonitors.get(i).h ? location.getBlockY() - activeMonitors.get(i).location.getBlockY()+activeMonitors.get(i).h : 0;
                    x = location.getBlockX() < activeMonitors.get(i).location.getBlockX() ? location.getBlockX() - activeMonitors.get(i).location.getBlockX() :
                            location.getBlockX() > activeMonitors.get(i).location.getBlockX()+activeMonitors.get(i).w ? location.getBlockX() - activeMonitors.get(i).location.getBlockX()-activeMonitors.get(i).w : 0;
                    distance = (int) Math.sqrt(x*x + y*y + z*z);
                case UP:
                case DOWN:
                    x = location.getBlockX() < activeMonitors.get(i).location.getBlockX() ? location.getBlockX() - activeMonitors.get(i).location.getBlockX() :
                            location.getBlockX() > activeMonitors.get(i).location.getBlockX()+activeMonitors.get(i).w ? location.getBlockX() - activeMonitors.get(i).location.getBlockX()-activeMonitors.get(i).w : 0;
                    z = location.getBlockZ() < activeMonitors.get(i).location.getBlockZ() ? location.getBlockZ() - activeMonitors.get(i).location.getBlockZ() :
                            location.getBlockZ() > activeMonitors.get(i).location.getBlockZ()+activeMonitors.get(i).w ? location.getBlockZ() - activeMonitors.get(i).location.getBlockZ()-activeMonitors.get(i).w : 0;

                default:
                    distance = 10000;
            }
            if(distance <= range){
                planeMonitors.add(activeMonitors.get(i));
            }
        }
        return planeMonitors;
    }
}
