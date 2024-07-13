package ladeEngine.PlayerData.types;

import ladeEngine.PlayerData.DataValue;

public class IntType extends DataValue {
    public int v;
    public IntType(String name,int value,boolean save){
        super.name = name;
        super.noSave = !save;
        this.v = value;
    }

    @Override
    public String getSaveData() {
        return String.valueOf(v);
    }

    @Override
    public void loadData(String data) {
        v = Integer.parseInt(data);
    }
}
