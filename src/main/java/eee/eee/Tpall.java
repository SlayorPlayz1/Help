package eee.eee;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpall implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("tpall")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("sa.tpall")) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.teleport(player.getLocation());
                    }
                    sender.sendMessage(ChatColor.GOLD + "Teleported all players to you!");

                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
            }

        }
        return true;
    }
}

