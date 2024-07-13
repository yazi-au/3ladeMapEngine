package ladeEngine.PlayerData;

import ladeEngine.Utils.GlobalFileTool;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class PlayerData {
    public Player player;
    public Block block;
    public boolean dontSave;
    public ArrayList<String> saveData = new ArrayList<>();
    public ArrayList<DataValue> datas = new ArrayList<>();
    public PlayerData(ArrayList<DataValue> datas){
        this.datas = datas;
    }
    public void save(File file){
        if(dontSave){
            return;
        }
        for (int i = 0; i < datas.size(); i++) {
            if(datas.get(i).noSave){
                continue;
            }
            saveData.add(datas.get(i).getSaveData());
        }
        GlobalFileTool.write(file,saveData);
        saveData = new ArrayList<>();
    }
    public void load(ArrayList<String> data){
        for (int i = 0; i < data.size(); i++) {
            if(datas.get(i).noSave){
                continue;
            }
            if(i > datas.size()){
                return;
            }
            datas.get(i).loadData(data.get(i));
        }
    }
    public DataValue search(String name){
        for (int i = 0; i < datas.size(); i++) {
            if(datas.get(i).name.equals(name)){
                return datas.get(i);
            }
        }
        return null;
    }
}
