package ladeEngine.PlayerData;

public abstract class DataValue {
    public boolean noSave;
    public String name;
    public abstract String getSaveData();
    public abstract void loadData(String data);
}
