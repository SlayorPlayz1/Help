package MoreCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmds implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equals("chatmsgs")){
            if (sender.hasPermission("sa.chatmsgs")){
                sender.sendMessage(ChatColor.RED + "Chat Msg Commands");
                sender.sendMessage(ChatColor.AQUA + "/helpme");
                sender.sendMessage(ChatColor.AQUA + "/oof");
                sender.sendMessage(ChatColor.AQUA + "/gg");
                sender.sendMessage(ChatColor.AQUA + "/idk");
                sender.sendMessage(ChatColor.AQUA + "/no");
                sender.sendMessage(ChatColor.AQUA + "/yes");
            }
        }

        Player player = (Player) sender;

        if (command.getName().equals("helpme")) {
            if (sender instanceof Player){
            if (sender.hasPermission("sa.helpme")) {
                player.chat(ChatColor.DARK_AQUA + "HELPME");
            }sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");

            }
            }
        if (command.getName().equals("oof")) {
            if (sender instanceof Player){
                if (sender.hasPermission("sa.oof")) {
                    player.chat(ChatColor.DARK_RED + "OOF");
                }sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");

            }
        }
        if (command.getName().equals("idk")) {
            if (sender instanceof Player){
                if (sender.hasPermission("sa.idk")) {
                    player.chat(ChatColor.BLACK + "Idk");
                }sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");

            }
        }

        if (command.getName().equals("relog")){
            if (sender.hasPermission("sa.relog")) {
                if (sender instanceof Player) {
                    try {
                        sender.sendMessage(ChatColor.RED + "Relogging!");
                        ((Player) sender).kickPlayer(ChatColor.RED + "Relogged");
                    } catch (NullPointerException nullPointerException) {
                        sender.sendMessage(ChatColor.DARK_RED + "Error!");
                    }
                }
            }else {
                sender.sendMessage(ChatColor.DARK_RED + "You do no have permission!");
            }
        }

        if (command.getName().equals("gg")) {
            if (sender instanceof Player){
                if (sender.hasPermission("sa.gg")) {
                    player.chat(ChatColor.GOLD + "GG");
                }sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");

            }
        }

        if (command.getName().equals("no")) {
            if (sender instanceof Player){
                if (sender.hasPermission("sa.no")) {
                    player.chat(ChatColor.RED + "No");
                }sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");

            }
        }





        return true;
    }
}
