package ladeEngine;

import ladeEngine.Event.EventManager;
import ladeEngine.Render.RenderProcess;

public class RunningProcess {
    Application app;
    //Monitor
    EventManager eventManager;
    RenderProcess renderProcess;
    public RunningProcess(Application app){
        this.app = app;
        eventManager = new EventManager();
        renderProcess = new RenderProcess(app);
    }
}
