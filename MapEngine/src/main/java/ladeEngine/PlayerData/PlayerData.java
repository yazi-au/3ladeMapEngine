package ladeEngine.PlayerData;

import ladeEngine.Utils.GlobalFileTool;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerData {
    public Player player;
    public boolean dontSave;
    public HashMap<String, DataValue> playerData = new HashMap<>();

    public PlayerData(ArrayList<DataValue> base){
        for (DataValue data : base) {
            playerData.put(data.name, data);
        }
    }
    public DataValue search(String name){
        return playerData.get(name);
    }
    public void save(File file) {
        if (dontSave) {
            return;
        }
        ArrayList<String> saveData = new ArrayList<>();
        for (DataValue data : playerData.values()) {
            if (!data.noSave) {
                saveData.add(data.getSaveData());
            }
        }
        GlobalFileTool.write(file, saveData);
    }

    public void load(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            String name = data.get(i);
            DataValue dataValue = playerData.get(name);
            if (dataValue != null && !dataValue.noSave) {
                dataValue.loadData(data.get(i));
            }
        }
    }

    public void clearData() {
        playerData.clear();
    }
}