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

public class CmdUnban implements CommandExecutor {
    public CmdUnban(SABans plugin) {}

    public CmdUnban() {

    }

    public void msg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public void bc(String msg, String perm) {
        Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', msg), perm);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("unban")) {
            if (!sender.hasPermission("smartbans.unban") && sender instanceof org.bukkit.entity.Player) {
                msg(sender, "&4You do not have access to that command.");
                return true;
            }
            if (args.length == 0) {
                msg(sender, "&cCorrect usage: /unban <username>");
                return true;
            }
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[0]);
            String uuid = p.getUniqueId().toString();
            File banFile = new File(SABans.getInstance().getDataFolder(), "banned.yml");
            YamlConfiguration yamlConfiguration1 = YamlConfiguration.loadConfiguration(banFile);
            File prevBanFile = new File(SABans.getInstance().getDataFolder(), "prev-banned.yml");
            YamlConfiguration yamlConfiguration2 = YamlConfiguration.loadConfiguration(prevBanFile);
            if (!yamlConfiguration1.contains("ban-list." + uuid.toString())) {
                msg(sender, SABans.getInstance().getConfig().getString("strings.player-not-banned").replaceAll("%player%", args[0]));
                return true;
            }
            String pName = yamlConfiguration1.getString("ban-list." + uuid.toString() + ".name");
            String bannedBy = yamlConfiguration1.getString("ban-list." + uuid.toString() + ".banned-by");
            String banLength = yamlConfiguration1.getString("ban-list." + uuid.toString() + ".ban-length");
            boolean permanent = yamlConfiguration1.getBoolean("ban-list." + uuid.toString() + ".permanent");
            String reason = yamlConfiguration1.getString("ban-list." + uuid.toString() + ".reason");
            int s = yamlConfiguration2.getInt("do-not-change") + 1;
            yamlConfiguration2.set("do-not-change", Integer.valueOf(s));
            yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".name", pName);
            yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".banned-by", bannedBy);
            yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".unbanned-by", sender.getName());
            yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".ban-length", banLength);
            yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".permanent", Boolean.valueOf(permanent));
            yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".reason", reason);
            yamlConfiguration1.set("ban-list." + uuid.toString(), null);
            try {
                yamlConfiguration1.save(banFile);
                yamlConfiguration2.save(prevBanFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bc(SABans.getInstance().getConfig().getString("strings.unban-notif").replaceAll("%player%", Bukkit.getServer().getOfflinePlayer(args[0]).getName()).replaceAll("%NL%", "\n").replaceAll("%unbanned-by%", sender.getName()), "smartbans.notify");
            return true;
        }
        return false;
    }
}

