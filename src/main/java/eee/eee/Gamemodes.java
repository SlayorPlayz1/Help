package eee.eee;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemodes implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("gms")){
            Player player = (Player) sender;
            if (args.length == 0) {
                if (sender instanceof Player) {
                    if (player.hasPermission("sa.gms")) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
            }
        }else{
            if (args.length == 1){
                if (sender.hasPermission("sa.gms.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    assert target != null;
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                    sender.sendMessage(ChatColor.GOLD + "Target's gamemode has been updated!");
                    if (target == null){
                        sender.sendMessage(ChatColor.DARK_RED + "Your target is not online!");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
            }
        }

        if(command.getName().equals("gmc")){
            Player player = (Player) sender;
            if (args.length == 0) {
                if (sender instanceof Player) {
                    if (player.hasPermission("sa.gmc")) {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }else{
                if (args.length == 1){
                    if (player.hasPermission("sa.gmc.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        assert target != null;
                        target.setGameMode(GameMode.CREATIVE);
                        target.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                        sender.sendMessage(ChatColor.GOLD + "Target's gamemode has been updated!");
                        if (target == null){
                            sender.sendMessage(ChatColor.DARK_RED + "Your target is not online!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }
        }

        if(command.getName().equals("gma")){
            Player player = (Player) sender;
            if (sender instanceof Player){
                if (args.length == 0) {
                    if (player.hasPermission("sa.gma")) {
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }
        }else{
            if (args.length == 1){
                Player player = (Player) sender;
                if (player.hasPermission("sa.gma.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    assert target != null;
                    target.setGameMode(GameMode.ADVENTURE);
                    target.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                    sender.sendMessage(ChatColor.GOLD + "Target's gamemode has been updated!");
                    if (target == null){
                        sender.sendMessage(ChatColor.DARK_RED + "Your target is not online!");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
            }
        }

        return true;
    }
}
