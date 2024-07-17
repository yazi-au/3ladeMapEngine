package ladeEngine.PlayerData.Listener;

import ladeEngine.MapEngine;
import ladeEngine.PlayerData.PlayerData;
import ladeEngine.Utils.GlobalFileTool;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.ArrayList;

public class JoinE implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ArrayList<String> s = GlobalFileTool.read(new File(MapEngine.datasManager.path + e.getPlayer().getName()));
        PlayerData data;
        if(s == null){
            data = MapEngine.datasManager.newData(e.getPlayer());
        }else {
            data = new PlayerData(MapEngine.datasManager.baseData);
            data.load(s);
        }
        data.player = e.getPlayer();
        MapEngine.datasManager.datas.put(e.getPlayer(),data);
    }
}
