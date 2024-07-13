package ladeEngine.Monitor;

import ladeEngine.Utils.M3IVector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;

public class PlaneMonitor implements Monitor{
    public Location location;
    public BlockFace face;
    public ArrayList<Integer> mapIDs = new ArrayList<>();
    public int w,h;
    public int chunkX,chunkY;
    public PlaneMonitor(int chunkX,int chunkY){ //update all
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }
    public String save(){
        //World LocationX LocationY LocationZ Face w h mapIDs
        String a = new StringBuilder().append(location.getWorld().getName()).append("|").append(location.getBlockX()).append("|").append(location.getBlockY()).append("|").append(location.getBlockZ()).append("|").append(face.name()).append("|").append(w).append("|").append(h).toString();
        StringBuilder b = new StringBuilder(a);
        for (int i = 0; i < mapIDs.size(); i++) {
            b.append('|');
            b.append(mapIDs.get(i));
        }
        return b.toString();
    }
    public void load(String save){
        String[] a = save.split("|");
        location = new Location(Bukkit.getWorld(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2]),Integer.parseInt(a[3]));
        face = BlockFace.valueOf(a[4]);
        w = Integer.parseInt(a[5]);
        h = Integer.parseInt(a[6]);
        for (int i = 7; i < a.length; i++) {
            mapIDs.add(Integer.parseInt(a[i]));
        }
    }
}
