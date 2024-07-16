package ladeEngine;

import ladeEngine.Event.EventManager;
import ladeEngine.Monitor.HoldCursor;
import ladeEngine.Monitor.HoldMonitor;
import ladeEngine.Monitor.Monitor;
import ladeEngine.Monitor.PlaneMonitor;
import ladeEngine.Render.RenderProcess;
import org.bukkit.entity.Player;

public class RunningProcess {
    public Player player;
    public Application appData;
    public Monitor monitor;
    public EventManager eventManager;
    public RenderProcess renderProcess;
    public RunningProcess(Player player,Application app, Monitor monitor){
        this.player = player;
        this.appData = app;
        this.monitor = monitor;
        eventManager = new EventManager();
        renderProcess = new RenderProcess(app.width,app.height);
    }
    public boolean isHoldMonitor(){
        return monitor instanceof HoldMonitor;
    }
}
