package ladeEngine.GUI;

import ladeEngine.Application;
import ladeEngine.MapEngine;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getView().getTitle().startsWith(ChatColor.UNDERLINE+"|")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "<<< Last Page")){
                player.performCommand("meapps "+e.getCurrentItem().getItemMeta().getLore().get(0));
                return;
            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Next Page >>>")){
                player.performCommand("meapps "+e.getCurrentItem().getItemMeta().getLore().get(0));
                return;
            }
            Application app = MapEngine.apps.get(Integer.valueOf(e.getCurrentItem().getItemMeta().getLore().get(4)));
            app.setMap(player);
        }
    }
}
