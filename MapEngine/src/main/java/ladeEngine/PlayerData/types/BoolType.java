package ladeEngine.PlayerData.types;

import ladeEngine.PlayerData.DataValue;

public class BoolType extends DataValue {
    public boolean v;
    public BoolType(String name,boolean value,boolean save){
        super.name = name;
        super.noSave = !save;
        this.v = value;
    }

    @Override
    public String getSaveData() {
        return v ? "True" : "False";
    }

    @Override
    public void loadData(String data) {
        v = (data.equals("True"));
    }
}
