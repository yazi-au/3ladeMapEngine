package ladeEngine.Utils;

import ladeEngine.MapEngine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasicTools {
    private static HashMap<Integer,Float> scin = new HashMap<>();
    public static ArrayList<Player> getOnlinePlayers(){
        return new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
    }

    public static void addSpecialTag(Block block, String tagName, String tag) {
        block.setMetadata(tagName, new FixedMetadataValue(MapEngine.getPlugin(MapEngine.class), tag));
    }
    public static boolean hasSpecialTag(Block block, String tagName,String tag) {
        for (MetadataValue value : block.getMetadata(tagName)) {
            if (value.asString().equals(tag)) {
                return true;
            }
        }
        return false;
    }
    public static void removeSpecialTag(Block block,String tagName) {
        block.removeMetadata(tagName, MapEngine.getPlugin(MapEngine.class));
    }
    public static List<MetadataValue> getSpecialTag(Block block, String tagName){
        return block.getMetadata(tagName);
    }

    public static void init(){
        for (int i = 0; i < 90; i++) {
            scin.put(i,(float)Math.sin(i));
        }
    }

    public static float getSin(int alpha){
        if(alpha < 0){
            return scin.get(-Math.abs(10*alpha));
        }
        return scin.get(Math.abs(10*alpha));
    }

    public static float getCos(int alpha){
        return scin.get(Math.abs(90-10*alpha));
    }

    public static float getTan(int alpha){
        if(alpha < 0){
            return -getSin(10*alpha)/getCos(10*alpha);
        }
        return getSin(10*alpha)/getCos(10*alpha);
    }
}
