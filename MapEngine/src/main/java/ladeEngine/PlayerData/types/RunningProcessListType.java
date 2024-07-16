package ladeEngine.PlayerData.types;

import ladeEngine.Application;
import ladeEngine.PlayerData.DataValue;
import ladeEngine.RunningProcess;

import java.util.ArrayList;

public class RunningProcessListType extends DataValue {
    public ArrayList<RunningProcess> v = new ArrayList<>();
    public RunningProcessListType(String name, ArrayList<RunningProcess> value, boolean save){
        super.name = name;
        super.noSave = !save;
        this.v = value;
    }
    @Override
    public String getSaveData() {
        return null;
    }

    @Override
    public void loadData(String data) {
        v = new ArrayList<>();
    }
}
