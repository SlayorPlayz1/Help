package eee.eee;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FlyCommand implements CommandExecutor {

    private ArrayList<Player> list_of_flying_players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("fly")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    if (player.hasPermission("sa.fly")) {
                        if (list_of_flying_players.contains(player)) {
                            list_of_flying_players.remove(player);
                            player.setAllowFlight(false);
                            sender.sendMessage(ChatColor.GOLD + "Flight has been disabled!");
                        } else if (!(list_of_flying_players.contains(player))) {
                            list_of_flying_players.add(player);
                            player.setAllowFlight(true);
                            sender.sendMessage(ChatColor.GOLD + "Flight has been enabled!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                } else {
                    if (args.length == 1) {
                        if (player.hasPermission("sa.fly.others")) {
                            Player target = Bukkit.getPlayer(args[0]);
                            if (list_of_flying_players.contains(target)) {
                                list_of_flying_players.remove(target);
                                assert target != null;
                                target.setAllowFlight(false);
                                target.sendMessage(ChatColor.GOLD + "Flight has been disabled!");
                                sender.sendMessage(ChatColor.GOLD + "Target's flight has been disabled!!");
                            } else if (!(list_of_flying_players.contains(target))) {
                                list_of_flying_players.add(target);
                                assert target != null;
                                target.setAllowFlight(true);
                                target.sendMessage(ChatColor.GOLD + "Flight has been enabled!");
                                sender.sendMessage(ChatColor.GOLD + "Target's flight has been enabled!");
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                            }
                        }
                    }
                }
            }sender.sendMessage("");
        }
        return true;
    }
}

