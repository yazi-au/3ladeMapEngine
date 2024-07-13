package ladeEngine.Render;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapPalette;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

public class SendMap {
    public static void sendMap(Player player, int id, BufferedImage image,MapCursor[] cursors){
        // 创建对应的 packetPlayOutMap 数据包
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.MAP);
// 写入地图的缩放级别
        packet.getBytes().write(0, (byte) 4);
// 写入 MapId
        packet.getIntegers().write(0, 0);
// 写入起始 x 点
        packet.getIntegers().write(1, 0);
// 写入起始 y 点
        packet.getIntegers().write(2, 0);
// 写入列数
        packet.getIntegers().write(3, 128);
// 写入行数
        packet.getIntegers().write(4, 128);
        packet.getBooleans().write(0, false);
// 写入一个空的图标数组
        packet.getModifier().write(3, cursors);
        byte[] bytes = MapPalette.imageToBytes(image);
// 写入像素点数组
        packet.getByteArrays().write(0, bytes);
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    }
}
