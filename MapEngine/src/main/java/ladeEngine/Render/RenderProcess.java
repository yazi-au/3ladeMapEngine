package ladeEngine.Render;

import ladeEngine.Application;
import ladeEngine.GameMap;

import java.awt.image.BufferedImage;

public class RenderProcess {
    BufferedImage frameBuffer;
    public RenderProcess(int w,int h){
        frameBuffer = new BufferedImage(w*128,h*128,1);
    }
    public void update(GameMap map){
        map.draw(frameBuffer,frameBuffer.createGraphics());
    }
}
