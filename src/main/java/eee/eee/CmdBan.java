package eee.eee;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CmdBan implements CommandExecutor {
    public CmdBan(SABans plugin) {}

    public CmdBan() {

    }

    public void msg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public void bc(String msg, String perm) {
        Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', msg), perm);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ban")) {
            if (!sender.hasPermission("smartbans.ban") && sender instanceof Player) {
                msg(sender, "&4You do not have access to that command.");
                return true;
            }
            if (args.length == 0) {
                msg(sender, "&cCorrect usage: /ban <username> [reason]");
                return true;
            }
            if (Bukkit.getServer().getOfflinePlayer(args[0]) == null) {
                msg(sender, SABans.getInstance().getConfig().getString("strings.player-not-found").replaceAll("%player%", args[0]));
                return true;
            }
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[0]);
            String uuid = p.getUniqueId().toString();
            File banFile = new File(SABans.getInstance().getDataFolder(), "banned.yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(banFile);
            if (yamlConfiguration.contains("ban-list." + uuid)) {
                msg(sender, SABans.getInstance().getConfig().getString("strings.already-banned").replaceAll("%player%", p.getName()));
                return true;
            }
            StringBuilder builder = new StringBuilder();
            String reason = SABans.getInstance().getConfig().getString("strings.default-reason");
            int i = 1;
            if (args.length > 1) {
                while (i < args.length) {
                    builder.append(String.valueOf(args[i]) + " ");
                    i++;
                }
                if (!builder.equals(""))
                    reason = builder.toString();
                reason = reason.trim();
            }
            yamlConfiguration.set("ban-list." + uuid + ".name", p.getName());
            yamlConfiguration.set("ban-list." + uuid + ".banned-by", sender.getName());
            yamlConfiguration.set("ban-list." + uuid + ".ban-length", SABans.getInstance().getConfig().getString("strings.default-perm"));
            yamlConfiguration.set("ban-list." + uuid + ".permanent", Boolean.valueOf(true));
            yamlConfiguration.set("ban-list." + uuid + ".reason", reason);
            try {
                yamlConfiguration.save(banFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bc(SABans.getInstance().getConfig().getString("strings.perm-banned-notif").replaceAll("%player%", p.getName()).replaceAll("%banned-by%", sender.getName()).replaceAll("%reason%", reason).replaceAll("%NL%", "\n"), "smartbans.notify");
            if (p.isOnline()) {
                String kickMsg = ChatColor.translateAlternateColorCodes('&', SABans.getInstance().getConfig().getString("strings.ban-message")
                        .replaceAll("%NL%", "\n")
                        .replaceAll("%banned-by%", yamlConfiguration.getString("ban-list." + uuid + ".banned-by"))
                        .replaceAll("%ban-length%", yamlConfiguration.getString("ban-list." + uuid + ".ban-length"))
                        .replaceAll("%reason%", yamlConfiguration.getString("ban-list." + uuid + ".reason")));
                ((Player)p).kickPlayer(kickMsg);
            }
            return true;
        }
        return false;
    }
}

