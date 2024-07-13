package ladeEngine.Monitor;

import java.util.ArrayList;

public class PlaneMonitorManager {
    public static ArrayList<PlaneMonitor> activeMonitors = new ArrayList<>();
    public static void addMonitor(PlaneMonitor monitor){
        activeMonitors.add(monitor);
    }
}
