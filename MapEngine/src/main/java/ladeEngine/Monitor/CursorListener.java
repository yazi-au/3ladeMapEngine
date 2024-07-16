package ladeEngine.Monitor;

import ladeEngine.MapEngine;
import ladeEngine.PlayerData.types.BoolType;
import ladeEngine.PlayerData.types.RunningProcessListType;
import ladeEngine.RunningProcess;
import ladeEngine.Utils.BasicTools;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class CursorListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!BasicTools.isLoreStartingWith(e.getPlayer(),"||")) return;
        if(e.getFrom().getYaw() != e.getTo().getYaw() || e.getFrom().getPitch() != e.getTo().getPitch()){
            ArrayList<RunningProcess> rps = ((RunningProcessListType) MapEngine.datasManager.search(e.getPlayer().getName()).search("running")).v;
            for (int i = 0; i < rps.size(); i++) {
                if (rps.get(i).monitor instanceof HoldMonitor) {
                    HoldMonitor m = (HoldMonitor) rps.get(i).monitor;
                    if (m.cursor != null) {
                        m.cursor.move(e.getFrom().getYaw(), e.getFrom().getPitch(), e.getTo().getYaw(), e.getTo().getPitch());
                        ArrayList<MapIcon> icons = new ArrayList<>();
                        icons.add(m.cursor.getIcon());
                        rps.get(i).renderProcess.pushImage(e.getPlayer(), rps.get(i).monitor, icons);
                    }
                }
            }
        }
    }
}
