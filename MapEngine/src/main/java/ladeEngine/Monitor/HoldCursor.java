package ladeEngine.Monitor;

import ladeEngine.Event.EventManager;
import ladeEngine.Event.Events.MouseMoveEvent;
import ladeEngine.RunningProcess;
import net.minecraft.world.level.saveddata.maps.MapIcon;

public class HoldCursor {
    public RunningProcess rp;
    public MapIcon.Type type = MapIcon.Type.a;
    private int x,y;
    private float lastYaw,lastPitch;
    public byte rotation = 2;
    public float sensitive = 2.6f;
    public HoldCursor(RunningProcess rp){
        this.rp = rp;
    }
    //{OLDX,OLDY,X,Y}
    public int[] move(float yaw, float pitch) {
        int[] r = new int[]{x, y, 0, 0};
        float deltaYaw = ((yaw - lastYaw) + 540) % 360 - 180;

        x += sensitive * deltaYaw;
        x = Math.max(-127, Math.min(x, 127));

        float pitchRange = 90 - 30;
        float yRange = 127 - (-127);
        float normalizedPitch = (pitch - 30) / pitchRange;
        float interpolatedY = -127 + normalizedPitch * yRange;

        y += sensitive * (interpolatedY - y);
        y = Math.max(-127, Math.min(y, 127));

        r[2] = x;
        r[3] = y;
        MouseMoveEvent e = new MouseMoveEvent();
        e.lastX = r[0];
        e.lastY = r[1];
        e.x = r[2];
        e.y = r[3];
        lastYaw = yaw;
        lastPitch = pitch;
        rp.eventManager.call("MouseMoveEvent", e);
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
