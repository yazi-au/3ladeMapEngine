package ladeEngine.Render;

import net.minecraft.network.protocol.game.PacketPlayOutMap;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapPalette;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;

public class SendMap {
    public static void sendMap(Player player, int id, BufferedImage image, Collection<MapIcon> cursors){
        WorldMap.b mapSection = new WorldMap.b(0, 0, 128, 128, MapPalette.imageToBytes(image));
        PacketPlayOutMap packet = new PacketPlayOutMap(id, (byte)(4+4),false,cursors, mapSection);
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        entityPlayer.b.a(packet);
    }
    public static byte[] imageToBytes(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        byte[] bytes = new byte[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb, true);
                bytes[y * width + x] = matchColor(color);
            }
        }

        return bytes;
    }

    private static byte matchColor(Color color) {
        if (color.equals(Color.BLACK)) {
            return 0;
        } else if (color.equals(Color.WHITE)) {
            return 1;
        } else {
            return -1; // 默认值
        }
    }
}
