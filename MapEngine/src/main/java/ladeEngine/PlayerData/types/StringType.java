package ladeEngine.PlayerData.types;

import ladeEngine.PlayerData.DataValue;

public class StringType extends DataValue {
    public String v;
    public StringType(String name,String value,boolean save){
        super.name = name;
        super.noSave = !save;
        this.v = value;
    }

    @Override
    public String getSaveData() {
        return v;
    }

    @Override
    public void loadData(String data) {
        v = data;
    }
}
