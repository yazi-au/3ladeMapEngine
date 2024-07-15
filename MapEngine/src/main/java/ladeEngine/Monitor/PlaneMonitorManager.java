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
                if(mapIDs.get(j) == id) return true;
            }
        }
        return false;
    }
    public static ArrayList<PlaneMonitor> getNearMonitor(Location location,int range){
        ArrayList<PlaneMonitor> planeMonitors = new ArrayList<>();
        int distance = 0;
        for (int i = 0; i < activeMonitors.size(); i++) {
            distance = activeMonitors.get(i).getDistance(activeMonitors.get(i).face,location);
            if(distance <= range){
                planeMonitors.add(activeMonitors.get(i));
            }
        }
        return planeMonitors;
    }
}
