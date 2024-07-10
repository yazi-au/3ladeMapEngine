package lademapengine.lademapengine.Engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderProcess {
    public BufferedImage frame;
    public Application application;
    public Graphics2D graphics2D;
    public RenderProcess(Application application){
        this.application = application;
        frame = new BufferedImage((int)application.frameSize.x,(int)application.frameSize.y,1);
        graphics2D = frame.createGraphics();
    }
    public void update(){
        if(application.gameObjects == null) return;
        for (int i = 0; i < application.gameObjects.size(); i++) {
            application.gameObjects.get(i).draw(frame,graphics2D);
        }
    }
}
