package ladeEngine;

import ladeEngine.Event.EventManager;
import ladeEngine.PlayerData.types.BoolType;
import ladeEngine.PlayerData.types.LocationType;
import ladeEngine.PlayerData.types.StringType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class Application {
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
    public abstract Application getApplication();
    public abstract void onEnable();
    public abstract void onDisable();
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
        lore.add("-[3lade Map Engine Application]-");
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
            meta.setLore(lore);
            item.setItemMeta(meta);
            player.getInventory().setItemInMainHand(item);
            player.sendMessage("Application was installed on your main hand map");
            player.closeInventory();
        }else{
            ((BoolType)MapEngine.datasManager.search(player).search("setUpEnable")).v = true;
            ((StringType)MapEngine.datasManager.search(player).search("setUpApp")).v = name;
            player.closeInventory();
            player.sendMessage(ChatColor.AQUA+"Please Click First Map Block");
        }
    }
}
