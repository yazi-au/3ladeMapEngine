package ladeEngine.Monitor;

import ladeEngine.Application;
import ladeEngine.MapEngine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.Collections;

public class PlaneMonitor implements Monitor{
    public Location location;
    public BlockFace face;
    public ArrayList<Integer> mapIDs = new ArrayList<>();
    public int w, h;
    public int chunkX, chunkY;
    public Application appBase;

    public PlaneMonitor(int chunkX, int chunkY,Application app) { //update all
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public PlaneMonitor(int chunkX, int chunkY) { //update all
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public String save() {
        //World LocationX LocationY LocationZ Face w h app mapIDs
        String a = new StringBuilder().append(location.getWorld().getName()).append("|").append(location.getBlockX()).append("|").append(location.getBlockY()).append("|").append(location.getBlockZ()).append("|").append(face.name()).append("|").append(w).append("|").append(h).toString();
        StringBuilder b = new StringBuilder(a);
        b.append(appBase.name);
        for (int i = 0; i < mapIDs.size(); i++) {
            b.append('|');
            b.append(mapIDs.get(i));
        }
        return b.toString();
    }

    public void load(String save) {
        String[] a = save.split("|");
        location = new Location(Bukkit.getWorld(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]));
        face = BlockFace.valueOf(a[4]);
        w = Integer.parseInt(a[5]);
        h = Integer.parseInt(a[6]);
        appBase = MapEngine.searchApp(a[7]);
        for (int i = 7; i < a.length; i++) {
            mapIDs.add(Integer.parseInt(a[i]));
        }
        sort();
    }

    private void sort(){
        switch (face) {
            case NORTH:
            case WEST:
                for (int i = 0; i < h; i++) {
                    ArrayList<Integer> back = new ArrayList<>();
                    for (int j = 0; j < w; j++) {
                        back.add(mapIDs.get(h * w + j));
                    }
                    Collections.reverse(back);
                    for (int j = 0; j < w; j++) {
                        back.set(mapIDs.get(h * w + j), back.get(j));
                    }
                }
        }
    }

    public int getDistance(BlockFace face,Location location1) {
        int distance = 0;
        int x = 0,y = 0,z = 0;
        switch (face) {
            case EAST:
            case WEST:
                x = Math.abs(location1.getBlockX() - location.getBlockX());
                y = location1.getBlockY() > location.getBlockY() ? location1.getBlockY() - location.getBlockY() :
                        location1.getBlockY() < location.getBlockY() - h ? location1.getBlockY() - location.getBlockY() + h : 0;
                z = location1.getBlockZ() < location.getBlockZ() ? location1.getBlockZ() - location.getBlockZ() :
                        location1.getBlockZ() > location.getBlockZ() + w ? location1.getBlockZ() - location.getBlockZ() - w : 0;
                distance = (int) Math.sqrt(x * x + y * y + z * z);
            case SOUTH:
            case NORTH:
                z = location1.getBlockZ() - location.getBlockZ();
                y = location1.getBlockY() > location.getBlockY() ? location1.getBlockY() - location.getBlockY() :
                        location1.getBlockY() < location.getBlockY() - h ? location1.getBlockY() - location.getBlockY() + h : 0;
                x = location1.getBlockX() < location.getBlockX() ? location1.getBlockX() - location.getBlockX() :
                        location1.getBlockX() > location.getBlockX() + w ? location1.getBlockX() - location.getBlockX() - w : 0;
                distance = (int) Math.sqrt(x * x + y * y + z * z);
            case UP:
            case DOWN:
                x = location1.getBlockX() < location.getBlockX() ? location1.getBlockX() - location.getBlockX() :
                        location1.getBlockX() > location.getBlockX() + w ? location1.getBlockX() - location.getBlockX() - w : 0;
                z = location1.getBlockZ() < location.getBlockZ() ? location1.getBlockZ() - location.getBlockZ() :
                        location1.getBlockZ() > location.getBlockZ() + w ? location1.getBlockZ() - location.getBlockZ() - w : 0;
                y = location1.getBlockY() - location.getBlockY();
                distance = (int) Math.sqrt(x * x + y * y + z * z);
            default:
                distance = 10000;
        }
        return distance;
    }
}
