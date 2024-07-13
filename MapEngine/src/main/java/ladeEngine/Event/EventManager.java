package ladeEngine.Event;

import ladeEngine.MapEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    public HashMap<String, ArrayList<EventListener>> events = new HashMap<>();
    public EventManager(){
        for (int i = 0; i < MapEngine.basicEvents.size(); i++) {
            registerEvent(MapEngine.basicEvents.get(i));
        }
    }
    public void registerEvent(String name){
        events.put(name,new ArrayList<>());
    }
    public void call(String name,Event event){
        if(events.containsKey(name)){
            ArrayList<EventListener> listeners = events.get(name);
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).run(event);
            }
        }
    }
}
