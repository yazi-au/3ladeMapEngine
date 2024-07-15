package ladeEngine.Test;

import ladeEngine.Application;
import ladeEngine.GameMap;

public class TestApplication extends Application {
    public TestApplication() {
        super("test","no version","yazi_au",1,1);
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
    }

    @Override
    public void onDisable() {
        System.out.println("TestApplication Stop");
    }
}
