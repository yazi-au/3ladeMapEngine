package ladeEngine.Monitor;

import ladeEngine.Event.EventManager;
import ladeEngine.Event.Events.MouseMoveEvent;
import ladeEngine.RunningProcess;
import net.minecraft.world.level.saveddata.maps.MapIcon;

public class HoldCursor {
    public RunningProcess rp;
    public MapIcon.Type type = MapIcon.Type.a;
    private int x,y;
    public byte rotation = 4;
    public float sensitive = 0.6f;
    public HoldCursor(RunningProcess rp){
        this.rp = rp;
    }
    //{OLDX,OLDY,X,Y}
    public int[] move(float lastYaw, float lastPitch, float yaw, float pitch) {
        int[] r = new int[]{x, y, 0, 0};
        float deltaYaw = ((yaw - lastYaw) + 540) % 360 - 180;
        float deltaPitch = ((pitch - lastPitch) + 540) % 360 - 180;

        x += sensitive * deltaYaw;
        x = Math.max(-127, Math.min(x, 127));

        y += sensitive * deltaPitch;
        if(pitch == 90) y = 127;
        y = Math.max(-127, Math.min(y, 127));

        r[2] = x;
        r[3] = y;
        MouseMoveEvent e = new MouseMoveEvent();
        e.lastX = r[0];
        e.lastY = r[1];
        e.x = r[2];
        e.y = r[3];
        rp.eventManager.call("MouseMoveEvent",e);
        return r;
    }
    public MapIcon getIcon(){
        return new MapIcon(type,(byte)x,(byte)y,rotation,null);
    }
    public void setXY(int x,int y){
        MouseMoveEvent e = new MouseMoveEvent();
        e.lastX = this.x;
        e.lastY = this.y;
        e.x = x;
        e.y = y;
        rp.eventManager.call("MouseMoveEvent",e);
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
