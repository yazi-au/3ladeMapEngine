package ladeEngine.PlayerData.types;

import ladeEngine.PlayerData.DataValue;

public class FloatType extends DataValue {
    public float v;
    public FloatType(String name,float value,boolean save){
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
        v = Float.parseFloat(data);
    }
}
