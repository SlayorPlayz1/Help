package eee.eee;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("serveraddons")){
            if (args.length == 0){
                sender.sendMessage(ChatColor.DARK_RED + "Did not specify enough!");
                sender.sendMessage(ChatColor.RED + "/sa admin");
                sender.sendMessage(ChatColor.RED + "/sa help");
                sender.sendMessage(ChatColor.RED + "/sa version");
            }else if (args.length >= 1){
                if (args[0].equalsIgnoreCase("help")){
                    if (sender.hasPermission("sa.help")){
                        sender.sendMessage(ChatColor.RED + "Server Addons Help!");
                        sender.sendMessage(ChatColor.AQUA + "/discord");
                        sender.sendMessage(ChatColor.AQUA + "/chatmsgs");
                        sender.sendMessage(ChatColor.AQUA + "/relog");
                    }else{
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }else if (args[0].equalsIgnoreCase("version")){
                    if (sender.hasPermission("sa.version")){
                        sender.sendMessage(ChatColor.GOLD + "Running ServerAddons version " + ChatColor.RED + "1.0.0a" );
                    }else{
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }else if (args[0].equalsIgnoreCase("admin")){
                    if (sender.hasPermission("sa.admin")){
                        sender.sendMessage(ChatColor.RED + "Server Addons Admin Help!");
                        sender.sendMessage(ChatColor.AQUA + "/fly");
                        sender.sendMessage(ChatColor.AQUA + "/fly (player)");
                        sender.sendMessage(ChatColor.AQUA + "/tpall");
                        sender.sendMessage(ChatColor.AQUA + "/gma");
                        sender.sendMessage(ChatColor.AQUA + "/gmc");
                        sender.sendMessage(ChatColor.AQUA + "/gms");
                        sender.sendMessage(ChatColor.AQUA + "/kick (player)");
                        sender.sendMessage(ChatColor.AQUA + "/ban (player)");
                    }else{
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }
        }


        return true;
    }
}
