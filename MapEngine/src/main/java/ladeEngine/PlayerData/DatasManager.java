package ladeEngine.PlayerData;

import ladeEngine.PlayerData.Listener.JoinE;
import ladeEngine.PlayerData.Listener.QuitE;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatasManager {
    public ArrayList<DataValue> baseData = new ArrayList<>();
    public Map<Player, PlayerData> datas = new HashMap<>();
    public String path;

    public DatasManager(String path, Plugin plugin) {
        this.path = path;
        File a = new File(path);
        if (!a.exists()) {
            a.mkdirs();
        }
        plugin.getServer().getPluginManager().registerEvents(new JoinE(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new QuitE(), plugin);
    }

    public PlayerData search(Player player) {
        return datas.get(player);
    }

    public void delete(Player player) {
        PlayerData playerData = datas.remove(player);
        if (playerData != null) {
            playerData.playerData.clear();
        }
    }

    public void addValue(DataValue value) {
        baseData.add(value);
    }

    public PlayerData newData(Player player) {
        PlayerData d = new PlayerData(baseData);
        datas.put(player, d);
        return d;
    }
}
