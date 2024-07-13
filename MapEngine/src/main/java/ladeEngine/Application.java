package ladeEngine;

import ladeEngine.Event.EventManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Application {
    public String name;
    public String description = "No description";
    public String version;
    public String author;
    public int width;
    public int height;
    public int ID;
    public ArrayList<GameMap> maps = new ArrayList<>();
    public Application(String name,String version,String author,int width,int height){
        this.name = name;
        this.author = author;
        this.height = height;
        this.version = version;
        this.width = width;
    }
    public void addMap(GameMap map){
        this.maps.add(map);
    }
    public ItemStack getShowItem(){
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Description: "+description);
        lore.add(ChatColor.DARK_GRAY + "Version: "+version);
        lore.add(ChatColor.DARK_GRAY + "Author: "+author);
        lore.add(ChatColor.DARK_GRAY + "-[3lade Map Engine ladeEngine.Application]-");
        lore.add(""+ID);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public void setMap(Player player){
        if(player.getInventory().getItemInMainHand().getType().equals(Material.FILLED_MAP)){
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("||"+name);
        }else{
            player.sendMessage(ChatColor.AQUA+"Please Click First Map Block");
        }
    }
}
