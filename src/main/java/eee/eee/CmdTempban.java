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

public class CmdTempban implements CommandExecutor {
    public CmdTempban(SABans plugin) {}

    public CmdTempban() {

    }

    public void msg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public void bc(String msg, String perm) {
        Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', msg), perm);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tempban")) {
            if (!sender.hasPermission("smartbans.tempban") && sender instanceof Player) {
                msg(sender, "&4You do not have access to that command.");
                return true;
            }
            if (args.length < 2) {
                msg(sender, "&cCorrect usage: /tempban <username> <time> [reason]");
                return true;
            }
            if (Bukkit.getServer().getOfflinePlayer(args[0]) == null) {
                msg(sender, SABans.getInstance().getConfig().getString("strings.player-not-found").replaceAll("%player%", args[0]));
                return true;
            }
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[0]);
            long total = 0L;
            String[] time = args[1].split(",");
            for (int i = 0; i < time.length; i++) {
                if (time[i].replaceAll("[0-9]", "").length() != 1) {
                    msg(sender, "&cExample of correct usage: /tempban Notch 2h,10m Hacking");
                    msg(sender, "&cYou may use d, h, m, and/or s to indicate time.");
                    return true;
                }
                if (!time[i].replaceAll("[0-9]", "").contains("d") && !time[i].replaceAll("[0-9]", "").contains("h") && !time[i].replaceAll("[0-9]", "").contains("m") && !time[i].replaceAll("[0-9]", "").contains("s")) {
                    msg(sender, "&cExample of correct usage: /tempban Notch 2h,10m Hacking");
                    msg(sender, "&cYou may use d, h, m, and/or s to indicate time.");
                    return true;
                }
                long day = 0L, hour = 0L, minute = 0L, second = 0L;
                if (time[i].replaceAll("[0-9]", "").contains("d"))
                    day = (Integer.parseInt(time[i].replaceAll("[A-Za-z]", "")) * 86400);
                if (time[i].replaceAll("[0-9]", "").contains("h"))
                    hour = (Integer.parseInt(time[i].replaceAll("[A-Za-z]", "")) * 3600);
                if (time[i].replaceAll("[0-9]", "").contains("m"))
                    minute = (Integer.parseInt(time[i].replaceAll("[A-Za-z]", "")) * 60);
                if (time[i].replaceAll("[0-9]", "").contains("s"))
                    second = Integer.parseInt(time[i].replaceAll("[A-Za-z]", ""));
                total += day + hour + minute + second;
            }
            long newTime = total * 1000L + System.currentTimeMillis();
            String uuid = p.getUniqueId().toString();
            File banFile = new File(SABans.getInstance().getDataFolder(), "banned.yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(banFile);
            if (yamlConfiguration.contains("ban-list." + uuid)) {
                msg(sender, SABans.getInstance().getConfig().getString("strings.already-banned").replaceAll("%player%", p.getName()));
                return true;
            }
            StringBuilder builder = new StringBuilder();
            String reason = SABans.getInstance().getConfig().getString("strings.default-reason");
            int j = 2;
            if (args.length > 2) {
                while (j < args.length) {
                    builder.append(String.valueOf(args[j]) + " ");
                    j++;
                }
                if (!builder.equals(""))
                    reason = builder.toString();
                reason = reason.trim();
            }
            yamlConfiguration.set("ban-list." + uuid + ".name", p.getName());
            yamlConfiguration.set("ban-list." + uuid + ".banned-by", sender.getName());
            yamlConfiguration.set("ban-list." + uuid + ".ban-length", Long.valueOf(newTime));
            yamlConfiguration.set("ban-list." + uuid + ".permanent", Boolean.valueOf(false));
            yamlConfiguration.set("ban-list." + uuid + ".reason", reason);
            try {
                yamlConfiguration.save(banFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String timeban = Methods.getRemainingTime("", total);
            bc(SABans.getInstance().getConfig().getString("strings.temp-banned-notif").replaceAll("%player%", p.getName()).replaceAll("%banned-by%", sender.getName()).replaceAll("%reason%", reason).replaceAll("%NL%", "\n").replaceAll("%ban-length%", timeban), "smartbans.notify");
            if (p.isOnline()) {
                String kickMsg = ChatColor.translateAlternateColorCodes('&', SABans.getInstance().getConfig().getString("strings.ban-message")
                        .replaceAll("%NL%", "\n")
                        .replaceAll("%banned-by%", yamlConfiguration.getString("ban-list." + uuid + ".banned-by"))
                        .replaceAll("%ban-length%", timeban)
                        .replaceAll("%reason%", yamlConfiguration.getString("ban-list." + uuid + ".reason")));
                ((Player)p).kickPlayer(kickMsg);
            }
            return true;
        }
        return false;
    }
}

