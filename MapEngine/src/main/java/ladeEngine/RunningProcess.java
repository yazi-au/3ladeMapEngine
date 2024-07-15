package ladeEngine;

import ladeEngine.Event.EventManager;
import ladeEngine.Monitor.HoldMonitor;
import ladeEngine.Monitor.PlaneMonitor;
import ladeEngine.Render.RenderProcess;
import org.bukkit.entity.Player;

public class RunningProcess {
    public Player player;
    public Application appData;
    public HoldMonitor hold;
    public PlaneMonitor plane;
    public EventManager eventManager;
    public RenderProcess renderProcess;
    public RunningProcess(Player player,Application app, HoldMonitor monitor){
        this.player = player;
        this.appData = app;
        this.hold = monitor;
        eventManager = new EventManager();
        renderProcess = new RenderProcess(app.width,app.height);
    }
    public RunningProcess(Player player,Application app, PlaneMonitor monitor){
        this.player = player;
        this.appData = app;
        this.plane = monitor;
        eventManager = new EventManager();
        renderProcess = new RenderProcess(app.width,app.height);
    }
}
