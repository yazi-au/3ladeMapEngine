package ladeEngine.PlayerData.types;

import ladeEngine.Application;
import ladeEngine.PlayerData.DataValue;
import ladeEngine.RunningProcess;

import java.util.ArrayList;

public class RunningProcessType extends DataValue {
    public ArrayList<RunningProcess> v = new ArrayList<>();
    public RunningProcessType(String name, ArrayList<RunningProcess> value, boolean save){
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
    }
}
