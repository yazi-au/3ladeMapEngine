package ladeEngine.Render;

import ladeEngine.Application;
import ladeEngine.GameMap;
import ladeEngine.Monitor.HoldMonitor;
import ladeEngine.Monitor.Monitor;
import ladeEngine.Monitor.PlaneMonitor;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RenderProcess {
    BufferedImage frameBuffer;
    public RenderProcess(int w,int h){
        frameBuffer = new BufferedImage(w*128,h*128,1);
    }
    public void clean(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
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
                SendMap.sendMap(player,plane.mapIDs.get(i),cropSubImage(frameBuffer,(i%plane.h)*128,((int)(i/plane.h))*128,
                        (i%plane.h)*128+128,((int)(i/plane.h))*128+128), null);
            }
        }
    }
    private static BufferedImage cropSubImage(BufferedImage source, int x1, int y1, int x2, int y2) {
        int width = x2 - x1 + 1;
        int height = y2 - y1 + 1;

        BufferedImage subImage = source.getSubimage(x1, y1, width, height);
        return subImage;
    }
}
