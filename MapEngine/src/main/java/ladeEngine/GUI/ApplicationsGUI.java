package ladeEngine.GUI;

import ladeEngine.MapEngine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ApplicationsGUI {
    public static void show(Player player,int page){
        Inventory inv = Bukkit.createInventory(null,54, ChatColor.UNDERLINE+"| MapEngine Application List");
        inv.setItem(45,getLastPageButton(page));
        inv.setItem(46,getLastPageButton(page));
        inv.setItem(47,getLastPageButton(page));
        inv.setItem(48,getLastPageButton(page));

        inv.setItem(49,getPageCount(page));

        inv.setItem(50,getNextPageButton(page));
        inv.setItem(51,getNextPageButton(page));
        inv.setItem(52,getNextPageButton(page));
        inv.setItem(53,getNextPageButton(page));
        for (int i = 0; i < 45; i++){
            if(MapEngine.apps.size() <= i+45*page) break;
            inv.addItem(MapEngine.apps.get(i+45*page).getShowItem());
        }
        player.openInventory(inv);
    }
    public static ItemStack getLastPageButton(int page){
        ItemStack lastPage = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = lastPage.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "<<< Last Page");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(""+(page-1));
        meta.setLore(lore);
        lastPage.setItemMeta(meta);
        return lastPage;
    }
    public static ItemStack getNextPageButton(int page){
        ItemStack lastPage = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = lastPage.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Next Page >>>");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(""+(page+1));
        meta.setLore(lore);
        lastPage.setItemMeta(meta);
        return lastPage;
    }
    public static ItemStack getPageCount(int page){
        ItemStack lastPage = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = lastPage.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "CurrentPage: "+(page+1));
        lastPage.setItemMeta(meta);
        return lastPage;
    }
}
