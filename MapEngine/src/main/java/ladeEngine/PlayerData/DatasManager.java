package ladeEngine.PlayerData;

import ladeEngine.PlayerData.Listener.JoinE;
import ladeEngine.PlayerData.Listener.QuitE;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;

public class DatasManager {
    public ArrayList<DataValue> baseData = new ArrayList<>();
    public ArrayList<PlayerData> datas = new ArrayList<>();
    public String path;
    public DatasManager(String path, Plugin plugin){
        this.path = path;
        File a = new File(path);
        if (!a.exists()) {
            a.mkdirs();
        }
        plugin.getServer().getPluginManager().registerEvents(new JoinE(),plugin);
        plugin.getServer().getPluginManager().registerEvents(new QuitE(),plugin);
    }
    public PlayerData search(String name){
        for (int i = 0; i < datas.size(); i++) {
            if(datas.get(i).player.getName().equals(name)){
                return datas.get(i);
            }
        }
        return null;
    }
    public void delete(String name){
        for (int i = 0; i < datas.size(); i++) {
            if(datas.get(i).player.getName().equals(name)){
                datas.get(i).datas.clear();
                datas.remove(i);
                return;
            }
        }
    }
    public void addValue(DataValue value){
        baseData.add(value);
    }
    public PlayerData newData(){
        PlayerData d = new PlayerData(baseData);
        datas.add(d);
        return d;
    }
}
