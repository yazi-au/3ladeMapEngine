package ladeEngine.Render;

import ladeEngine.Application;

import java.awt.image.BufferedImage;

public class RenderProcess {
    BufferedImage frameBuffer;
    public RenderProcess(Application app){
        frameBuffer = new BufferedImage(app.width*128,app.height*128,1);
    }
    public void update(){

    }
}
