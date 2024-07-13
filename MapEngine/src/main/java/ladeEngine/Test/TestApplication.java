package ladeEngine.Test;

import ladeEngine.Application;

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
    }

    @Override
    public void onDisable() {
        System.out.println("TestApplication Stop");
    }
}
