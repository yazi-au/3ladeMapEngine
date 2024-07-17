package ladeEngine.Test;

import ladeEngine.Application;
import ladeEngine.GameMap;
import ladeEngine.Render.GameObjects.ImageObject;
import ladeEngine.Utils.BasicTools;
import ladeEngine.Utils.M3IVector;

import java.io.IOException;

public class TestApplication extends Application {
    public TestApplication() {
        super("test","no version","yazi_au",2,2);
    }

    @Override
    public Application getApplication() {
        return new TestApplication();
    }

    @Override
    public void onEnable() {
        System.out.println("TestApplication Start");
        GameMap map = new GameMap();
        maps.add(map);
        try {
            map.addObject(new ImageObject(BasicTools.readImage("D:\\bg.png"),new M3IVector(0,0,0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        System.out.println("TestApplication Stop");
    }
}
