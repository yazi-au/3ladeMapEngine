package ladeEngine.PlayerData.Listener;

import ladeEngine.MapEngine;
import ladeEngine.PlayerData.types.IntType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class QuitE implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        MapEngine.datasManager.search(e.getPlayer().getName()).save(new File(MapEngine.datasManager.path+e.getPlayer().getName()));
        MapEngine.datasManager.delete(e.getPlayer().getName());
    }
}
