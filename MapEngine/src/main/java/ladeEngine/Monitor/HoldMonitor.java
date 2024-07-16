package ladeEngine.Monitor;

import ladeEngine.RunningProcess;

public class HoldMonitor implements Monitor{
    public HoldCursor cursor;
    public int mapId;
    public HoldMonitor(int mapId){
        this.mapId = mapId;
    }
}
