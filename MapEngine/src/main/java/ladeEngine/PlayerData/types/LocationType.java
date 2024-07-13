package ladeEngine.PlayerData.types;

import ladeEngine.PlayerData.DataValue;
import org.bukkit.Location;

public class LocationType extends DataValue {
    public Location v;
    public LocationType(String name,Location value,boolean save){
        this.name = name;
        this.v = value;
        this.noSave = !save;
    }

    @Override
    public String getSaveData() {
        return null;
    }

    @Override
    public void loadData(String data) {

    }
}
