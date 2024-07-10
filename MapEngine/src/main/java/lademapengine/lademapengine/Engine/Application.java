package lademapengine.lademapengine.Engine;

import lademapengine.lademapengine.Math.MVector;
import lademapengine.lademapengine.Monitor.MonitorType;

import java.util.List;

public abstract class Application {
    //Unique name
    String name;
    //Frame Size
    MVector frameSize;
    //Monitor Max Size(maxSize == null is no limit)
    MVector maxSize;
    //Monitor Min Size(minSize == null is 1x1 size)
    MVector minSize;
    //Available Monitor devices
    List<MonitorType> availableDevices;
    //DrawElements
    List<GameObject> gameObjects;
    //Application start
    public abstract boolean onStart();
    //Application stop
    public abstract boolean onStop();
}
