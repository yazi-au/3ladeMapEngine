package ladeEngine.Render;

import ladeEngine.GameMap;
import ladeEngine.Monitor.HoldMonitor;
import ladeEngine.Monitor.Monitor;
import ladeEngine.Monitor.PlaneMonitor;
import ladeEngine.Utils.BasicTools;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RenderProcess {
    BufferedImage frameBuffer;
    public RenderProcess(int w,int h){
        frameBuffer = new BufferedImage(w*128,h*128,1);
    }
    public void clean(Graphics2D g2d){
//        g2d.setColor(Color.CYAN);
//        g2d.fillRect(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
        try {
            g2d.drawImage(BasicTools.readImage("D:\\bg.png"),0,0,256,256,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update(GameMap map){
        Graphics2D g2d = frameBuffer.createGraphics();
        clean(g2d);
        map.draw(frameBuffer,g2d);
        g2d.dispose();
    }
    public void pushImage(Player player, Monitor monitor){
        if(monitor instanceof HoldMonitor){
            HoldMonitor hold = (HoldMonitor) monitor;
            SendMap.sendMap(player,hold.mapId,frameBuffer,null);
        }else if(monitor instanceof PlaneMonitor){
            PlaneMonitor plane = (PlaneMonitor) monitor;
            for (int i = 0; i < plane.mapIDs.size(); i++) {
                int x1 = (i % plane.h) * 128;
                int y1 = (i / plane.h) * 128;
                SendMap.sendMap(player,plane.mapIDs.get(i),frameBuffer.getSubimage(x1, y1, 128, 128), null);
            }
        }
    }
}
