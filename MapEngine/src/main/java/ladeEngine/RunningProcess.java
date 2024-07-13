package ladeEngine;

import ladeEngine.Event.EventManager;
import ladeEngine.Monitor.Monitor;
import ladeEngine.Render.RenderProcess;

public class RunningProcess {
    public Application app;
    Monitor monitor;
    public EventManager eventManager;
    public RenderProcess renderProcess;
    public RunningProcess(Application app,Monitor monitor){
        this.app = app;
        this.monitor = monitor;
        eventManager = new EventManager();
        renderProcess = new RenderProcess(app.width,app.height);
    }
}
