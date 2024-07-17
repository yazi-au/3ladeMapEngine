package ladeEngine.Monitor;

import ladeEngine.Event.Events.MouseClickEvent;
import ladeEngine.MapEngine;
import ladeEngine.PlayerData.types.BoolType;
import ladeEngine.PlayerData.types.RunningProcessListType;
import ladeEngine.RunningProcess;
import ladeEngine.Utils.BasicTools;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class CursorListener implements Listener {
    @EventHandler
    public void click(PlayerInteractEvent e){
        MouseClickEvent event = new MouseClickEvent();
        event.rightClick = e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR);
        //event.x =
        //RayCast update
    }
}
