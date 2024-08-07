package ladeEngine.Utils;

import ladeEngine.MapEngine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasicTools {
    private static HashMap<Integer,Float> scin = new HashMap<>();
    public static boolean isLoreStartingWith(Player player, String prefix) {
        return isLoreStartingWith(player.getInventory().getItemInMainHand(), prefix) ||
                isLoreStartingWith(player.getInventory().getItemInOffHand(), prefix);
    }
    private static boolean isLoreStartingWith(ItemStack item, String prefix) {
        if (item != null && item.getType() == Material.FILLED_MAP) {
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof MapMeta) {
                List<String> lore = meta.getLore();
                if (lore != null && !lore.isEmpty()) {
                    String lastLine = lore.get(lore.size() - 1);
                    return lastLine.startsWith(prefix);
                }
            }
        }
        return false;
    }
    public static BufferedImage readImage(String imagePath) throws IOException {
        File file = new File(imagePath);
        return ImageIO.read(file);
    }

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
