package ladeEngine.GUI;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AppListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) System.out.println(ChatColor.RED + "You cant use this command!");
        if(!((Player) sender).isOp()) ((Player) sender).sendMessage(ChatColor.RED + "You cant use this command!");
        if(args.length == 0) {
            ApplicationsGUI.show((Player) sender, 0);
        }else{
            if(!isNumeric(args[0])) ApplicationsGUI.show((Player) sender, 0);
            ApplicationsGUI.show((Player) sender, Integer.parseInt(args[0]));
        }
        return true;
    }
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
